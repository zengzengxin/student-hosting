package com.luwei.service.banner;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.banner.Banner;
import com.luwei.model.banner.BannerMapper;
import com.luwei.model.banner.pojo.cms.BannerAddDTO;
import com.luwei.model.banner.pojo.cms.BannerUpdateDTO;
import com.luwei.model.banner.pojo.cms.BannerVO;
import com.luwei.utils.ConversionBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-06
 */
@Service
public class BannerService extends ServiceImpl<BannerMapper, Banner> {

    public BannerVO saveBanner(BannerAddDTO addDTO) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(addDTO, banner);
        banner.setCreateTime(LocalDateTime.now());
        banner.setUpdateTime(LocalDateTime.now());
        save(banner);
        return toBannerVO(banner);
    }

    public void deleteBanner(Integer id) {
        baseMapper.deleteById(id);
    }

    public BannerVO updateBanner(BannerUpdateDTO updateDTO) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(updateDTO, banner);
        banner.setUpdateTime(LocalDateTime.now());
        boolean success = updateById(banner);
        Assert.isTrue(success, MessageCodes.BANNER_UPDATE_ERROR);
        return toBannerVO(banner);
    }

    public IPage<BannerVO> getPage(Page<Banner> page) {
        //Banner banner = new Banner();
        //QueryWrapper<Banner> queryWrapper = new QueryWrapper<>(banner);
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, null), this::toBannerVO);
    }

    private Banner getById(Integer id) {
        Banner banner = baseMapper.selectById(id);
        Assert.notNull(banner, "该轮播图ID不存在");
        return banner;
    }

    private BannerVO toBannerVO(Banner banner) {
        BannerVO bannerVO = new BannerVO();
        BeanUtils.copyProperties(banner, bannerVO);
        return bannerVO;
    }

}
