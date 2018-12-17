package com.luwei.service.hosting;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.hosting.Hosting;
import com.luwei.model.hosting.HostingMapper;
import com.luwei.model.hosting.pojo.cms.HostingAddDTO;
import com.luwei.model.hosting.pojo.cms.HostingQueryDTO;
import com.luwei.model.hosting.pojo.cms.HostingUpdateDTO;
import com.luwei.model.hosting.pojo.cms.HostingVO;
import com.luwei.model.hostingPackage.HostingPackage;
import com.luwei.model.hostingPackage.pojo.cms.HostingPackageAddDTO;
import com.luwei.model.hostingPackage.pojo.cms.HostingPackageVO;
import com.luwei.model.picture.Picture;
import com.luwei.model.picture.PictureMapper;
import com.luwei.model.picture.envm.PictureTypeEnum;
import com.luwei.service.hostingPackage.HostingPackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zzx
 * @since 2018-12-17
 */
@Service
@Slf4j
public class HostingService extends ServiceImpl<HostingMapper, Hosting> {

    @Resource
    HostingPackageService hostingPackageService;

    @Resource
    private PictureMapper pictureMapper;



    public HostingVO findById(Integer hostingId) {
        Hosting hosting = getById(hostingId);
        //TODO记得修改MessageCodes
        Assert.notNull(hosting, MessageCodes.HOSTING_IS_NOT_EXIST);
        return toHostingVO(hosting);
    }

    private HostingVO toHostingVO(Hosting hosting) {
        HostingVO hostingVO = new HostingVO();
        BeanUtils.copyNonNullProperties(hosting, hostingVO);
        List<HostingPackageVO> hostingPackageList = new ArrayList<HostingPackageVO>();
        hostingPackageList = hostingPackageService.findByHostingId(hosting.getHostingId());
        hostingVO.setHostingPackageList(hostingPackageList);
        return hostingVO;
    }

    //添加数据（向hosting表 picture表 套餐表）

    @Transactional
    public HostingVO saveHosting(HostingAddDTO hostingAddDTO) {

        Hosting hosting = new Hosting();
        BeanUtils.copyNonNullProperties(hostingAddDTO, hosting);
        LocalDateTime time = LocalDateTime.now();
        hosting.setUpdateTime(time);
        hosting.setCreateTime(time);
        //设置一些具体逻辑，是否需要加上deleted字段等等
        boolean isSuccess = save(hosting);
        Assert.isTrue(isSuccess, MessageCodes.HOSTING_SAVE_ERROR);
        log.info("保存数据---:{}", hosting);

        //向套餐表添加数据
        List<HostingPackageAddDTO> temp = hostingAddDTO.getHostingPackageList();
        for (HostingPackageAddDTO coursePackageAddDTO : temp) {
            hostingPackageService.saveHostingPackage(coursePackageAddDTO);
        }

        //向图片表添加数据
        // 保存课程图片
        List<String> urls = hostingAddDTO.getPictureUrls();
        for (String url : urls) {
            savePicture(url, hosting.getHostingId());
        }

        List<HostingPackageVO> list = temp.stream().map(this::toHostingVO2).collect(Collectors.toList());
        return toHostingVO(hosting).setHostingPackageList(list).setPictureUrls(urls);
    }


    private HostingPackageVO toHostingVO2(HostingPackageAddDTO hostingPackageAddDTO) {
        HostingPackageVO hostingPackageVO = new HostingPackageVO();
        BeanUtils.copyNonNullProperties(hostingPackageAddDTO, hostingPackageVO);
        return hostingPackageVO;
    }

    //保存图片
    private void savePicture(String url, Integer courseId) {
        Picture picture = new Picture();
        picture.setPictureUrl(url);
        // 图片类型为课程
        picture.setPictureType(PictureTypeEnum.HOSTING);
        // 外键ID
        picture.setForeignKeyId(courseId);
        LocalDateTime time = LocalDateTime.now();
        picture.setUpdateTime(time);
        picture.setCreateTime(time);

        int count = pictureMapper.insert(picture);
        Assert.isTrue(count > 0, MessageCodes.HOSTING_SAVE_ERROR);
    }



    @Transactional
    public void deleteHostings(Set<Integer> hostingIds) {
        //removeByIds删除0条也是返回true的，所以需要使用baseMapper
        int count = baseMapper.deleteBatchIds(hostingIds);
        Assert.isTrue(count == hostingIds.size(), MessageCodes.HOSTING_DELETE_ERROR);
        log.info("删除数据:ids{}", hostingIds);
    }


    @Transactional
    public HostingVO updateHosting(HostingUpdateDTO hostingUpdateDTO) {
        Hosting hosting = new Hosting();
        BeanUtils.copyNonNullProperties(hostingUpdateDTO, hosting);

        hosting.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(hosting), MessageCodes.HOSTING_IS_UPDATE_ERROR);



        //修改套餐
        List<HostingPackageAddDTO> hostingPackageList =hostingUpdateDTO.getHostingPackageList();
        for (HostingPackageAddDTO hostingPackageAddDTO: hostingPackageList) {
            HostingPackage hostingPackage = new HostingPackage();
            BeanUtils.copyNonNullProperties(hostingPackageAddDTO, hostingPackage);
            hostingPackageService.updateById(hostingPackage);
        }

        //修改图片
        pictureMapper.deleteByPictureTypeAndForeignKeyId(PictureTypeEnum.HOSTING.getValue(), hosting.getHostingId());
        // 保存课程图片
        List<String> urls = hostingUpdateDTO.getPictureUrls();
        Integer courseId = hosting.getHostingId();
        for (String url : urls) {
            savePicture(url, courseId);
        }

        log.info("修改数据：bean:{}", hostingUpdateDTO);
        List<HostingPackageVO> list = hostingPackageList.stream().map(this::toHostingVO2).collect(Collectors.toList());
        return findById(hosting.getHostingId()).setPictureUrls(urls).setHostingPackageList(list);
    }


    public IPage<HostingVO> findHostingPage(HostingQueryDTO hostingQueryDTO, Page page) {
        Hosting hosting = new Hosting();
        QueryWrapper<Hosting> wrapper = new QueryWrapper<>(hosting);
        if (hostingQueryDTO.getName() != null && !hostingQueryDTO.getName().equals("")) {
            wrapper.like("name", hostingQueryDTO.getName());
        }
        IPage<HostingVO> iPage = ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, wrapper), this::toHostingVO);
        List<HostingVO> list = iPage.getRecords();
        List collect = list.stream().map(this::dealWith).collect(Collectors.toList());

        return iPage;
    }

    private HostingVO dealWith(HostingVO hostingVO) {
        // 设置图片
        List<String> urls = pictureMapper.findAllByForeignKeyId(hostingVO.getHostingId());

        // 设置课程套餐列表
        List<HostingPackageVO> list = hostingPackageService.findByHostingId(hostingVO.getHostingId());

        return hostingVO.setHostingPackageList(list).setPictureUrls(urls);
    }
}
