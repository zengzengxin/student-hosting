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
import com.luwei.model.hosting.pojo.cms.*;
import com.luwei.model.picture.envm.PictureTypeEnum;
import com.luwei.model.recommend.Recommend;
import com.luwei.model.recommend.envm.ServiceTypeEnum;
import com.luwei.service.picture.PictureService;
import com.luwei.service.recommend.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zzx
 * @since 2018-12-17
 */
@Service
@Slf4j
public class HostingService extends ServiceImpl<HostingMapper, Hosting> {

    @Resource
    private PictureService pictureService;

    @Resource
    private RecommendService recommendService;

    private HostingCmsVO findById(Integer hostingId) {
        Hosting hosting = getById(hostingId);
        //TODO记得修改MessageCodes
        Assert.notNull(hosting, MessageCodes.HOSTING_IS_NOT_EXIST);
        return toHostingVO(hosting);
    }

    private HostingCmsVO toHostingVO(Hosting hosting) {
        HostingCmsVO hostingVO = new HostingCmsVO();
        BeanUtils.copyNonNullProperties(hosting, hostingVO);
        return hostingVO;
    }

    //添加数据（向hosting表 picture表 套餐表）

    @Transactional(rollbackFor = Exception.class)
    public HostingCmsVO saveHosting(HostingAddDTO hostingAddDTO) {

        Hosting hosting = new Hosting();
        BeanUtils.copyNonNullProperties(hostingAddDTO, hosting);
        LocalDateTime time = LocalDateTime.now();
        hosting.setUpdateTime(time);
        hosting.setCreateTime(time);
        //设置一些具体逻辑，是否需要加上deleted字段等
        boolean isSuccess = save(hosting);
        Assert.isTrue(isSuccess, MessageCodes.HOSTING_SAVE_ERROR);
        log.info("保存数据---:{}", hosting);

        //向图片表添加数据
        // 保存托管图片
        List<String> urls = hostingAddDTO.getPictureUrls();
        for (String url : urls) {
            pictureService.savePicture(url, hosting.getHostingId(),PictureTypeEnum.HOSTING);
        }
        return toHostingVO(hosting).setPictureUrls(urls);//
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteHostings(Set<Integer> hostingIds) {
        //removeByIds删除0条也是返回true的，所以需要使用baseMapper
        int count = baseMapper.deleteBatchIds(hostingIds);
        Assert.isTrue(count == hostingIds.size(), MessageCodes.HOSTING_DELETE_ERROR);
        log.info("删除数据:ids{}", hostingIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public HostingCmsVO updateHosting(HostingUpdateDTO hostingUpdateDTO) {
        Hosting hosting = new Hosting();
        BeanUtils.copyNonNullProperties(hostingUpdateDTO, hosting);

        hosting.setUpdateTime(LocalDateTime.now());

        //updateById不会把null的值赋值，修改成功后也不会赋值数据库所有的值
        Assert.isTrue(updateById(hosting), MessageCodes.HOSTING_IS_UPDATE_ERROR);

        //修改老图片图片
        pictureService.deleteByPictureTypeAndForeignKeyId(PictureTypeEnum.HOSTING.getValue(), hosting.getHostingId());
        // 保存课程图片
        List<String> urls = hostingUpdateDTO.getPictureUrls();
        Integer hostingId = hosting.getHostingId();
        for (String url : urls) {
            pictureService.savePicture(url, hostingId,PictureTypeEnum.HOSTING);
        }

        log.info("修改数据：bean:{}", hostingUpdateDTO);
        return findById(hosting.getHostingId()).setPictureUrls(pictureService.findAllByForeignKeyId(hosting.getHostingId(),PictureTypeEnum.HOSTING.getValue()));
    }

    public IPage<HostingCmsVO> findHostingPage(HostingQueryDTO hostingQueryDTO, Page<Hosting> page) {
        Hosting hosting = new Hosting();
        QueryWrapper<Hosting> wrapper = new QueryWrapper<>(hosting);
        if (hostingQueryDTO.getName() != null && !hostingQueryDTO.getName().equals("")) {
            wrapper.lambda().like(Hosting::getName, hostingQueryDTO.getName());
        }
        IPage<HostingCmsVO> iPage = ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, wrapper), this::toHostingVO);
        List<HostingCmsVO> list = iPage.getRecords();
        List<HostingCmsVO> collect = list.stream().map(this::dealWith).collect(Collectors.toList());
        iPage.setRecords(collect);
        return iPage;
    }

    private HostingCmsVO dealWith(HostingCmsVO hostingVO) {
        // 设置图片
        List<String> urls = pictureService.findAllByForeignKeyId(hostingVO.getHostingId(),PictureTypeEnum.HOSTING.getValue());

        return hostingVO.setPictureUrls(urls);
    }

    @Transactional(rollbackFor = Exception.class)
    public HostingCmsVO recommend(@Valid HostingRecommend hostingRecommend) {
        Hosting hosting = getById(hostingRecommend.getHostingId());
        Assert.notNull(hosting, MessageCodes.HOSTING_IS_NOT_EXIST);
        if (hostingRecommend.getRecommend()) {
            hosting.setRecommend(true);
            Assert.isTrue(updateById(hosting), MessageCodes.HOSTING_IS_UPDATE_ERROR);

            Recommend recommend = new Recommend();
            recommend.setServiceId(hosting.getHostingId())
                    .setServiceName(hosting.getName())
                    .setServicePrice(hosting.getPrice())
                    .setServiceIntroduction(hosting.getIntroduction())
                    .setServiceCoverUrl(hosting.getCoverUrl())
                    .setWeight(1)
                    .setSchoolId(hosting.getSchoolId())
                    .setServiceType(ServiceTypeEnum.HOSTING);

            // 创建时间 更新时间
            LocalDateTime now = LocalDateTime.now();
            recommend.setCreateTime(now).setUpdateTime(now);
            recommendService.save(recommend);
            return toHostingVO(hosting);
        }

        // 删除一条推荐数据
        hosting.setRecommend(false);
        Assert.isTrue(updateById(hosting), MessageCodes.HOSTING_IS_UPDATE_ERROR);
        boolean success = recommendService.realDeleteByServiceIdAndServiceType(hosting.getHostingId(), 1);
        Assert.isTrue(success, MessageCodes.RECOMMEND_DELETE_ERROR);

        return toHostingVO(hosting);
    }
}
