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
import com.luwei.model.picture.envm.PictureTypeEnum;
import com.luwei.service.picture.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
    private PictureService pictureService;



    private HostingVO findById(Integer hostingId) {
        Hosting hosting = getById(hostingId);
        //TODO记得修改MessageCodes
        Assert.notNull(hosting, MessageCodes.HOSTING_IS_NOT_EXIST);
        return toHostingVO(hosting);
    }

    private HostingVO toHostingVO(Hosting hosting) {
        HostingVO hostingVO = new HostingVO();
        BeanUtils.copyNonNullProperties(hosting, hostingVO);
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


        //向图片表添加数据
        // 保存课程图片
        List<String> urls = hostingAddDTO.getPictureUrls();
        for (String url : urls) {
            pictureService.savePicture(url, hosting.getHostingId());
        }
        return toHostingVO(hosting).setPictureUrls(urls);
    }


/*    private HostingPackageVO toHostingVO2(HostingPackageAddDTO hostingPackageAddDTO) {
        HostingPackageVO hostingPackageVO = new HostingPackageVO();
        BeanUtils.copyNonNullProperties(hostingPackageAddDTO, hostingPackageVO);
        return hostingPackageVO;
    }*/


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



        //修改图片
        pictureService.deleteByPictureTypeAndForeignKeyId(PictureTypeEnum.HOSTING.getValue(), hosting.getHostingId());
        // 保存课程图片
        List<String> urls = hostingUpdateDTO.getPictureUrls();
        Integer courseId = hosting.getHostingId();
        for (String url : urls) {
            pictureService.savePicture(url, courseId);
        }

        log.info("修改数据：bean:{}", hostingUpdateDTO);
        return findById(hosting.getHostingId()).setPictureUrls(urls);
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
        List<String> urls = pictureService.findAllByForeignKeyId(hostingVO.getHostingId());

        return hostingVO.setPictureUrls(urls);
    }
}
