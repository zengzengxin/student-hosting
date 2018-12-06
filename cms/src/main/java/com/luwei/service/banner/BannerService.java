package com.luwei.service.banner;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.model.banner.Banner;
import com.luwei.model.banner.BannerMapper;
import com.luwei.model.banner.pojo.cms.BannerAddDTO;
import com.luwei.model.banner.pojo.cms.BannerUpdateDTO;
import com.luwei.model.banner.pojo.cms.BannerVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-06
 */
@Service
public class BannerService extends ServiceImpl<BannerMapper, Banner> {

    public BannerVO saveBanner(@Valid BannerAddDTO addDTO) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(addDTO, banner);

        banner.setCreateTime(LocalDateTime.now());
        banner.setUpdateTime(LocalDateTime.now());
        System.out.println(banner);
        saveOrUpdate(banner);
        System.out.println(banner);
        return toBannerVO(banner);
    }

    private BannerVO toBannerVO(Banner banner) {
        BannerVO bannerVO = new BannerVO();
        BeanUtils.copyProperties(banner, bannerVO);
        return bannerVO;
    }

    public void deleteBanner(Integer id) {
        baseMapper.deleteById(id);
    }

    public BannerVO updateBanner(BannerUpdateDTO updateDTO) {
        Banner banner = baseMapper.selectById(updateDTO.getBannerId());
        Assert.notNull(banner, "该轮播图ID不存在");
        BeanUtils.copyProperties(updateDTO, banner);
        saveOrUpdate(banner);
        return toBannerVO(banner);
    }

    public Page<BannerVO> getPage(Page page) {
        //baseMapper.selectBannerPage

        return null;
    }
}
