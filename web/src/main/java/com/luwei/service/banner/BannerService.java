package com.luwei.service.banner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.banner.Banner;
import com.luwei.model.banner.BannerMapper;
import com.luwei.model.banner.pojo.web.BannerQuery;
import com.luwei.model.banner.pojo.web.BannerWebVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Service
@Slf4j
public class BannerService extends ServiceImpl<BannerMapper, Banner> {

    /**
     * 私有方法 将实体类转为对应的VO类
     *
     * @param banner
     * @return
     */
    private BannerWebVO toBannerWebVO(Banner banner) {
        BannerWebVO bannerVO = new BannerWebVO();
        BeanUtils.copyProperties(banner, bannerVO);
        return bannerVO;
    }

    /**
     * 分页获取Banner
     *
     * @param bannerQuery
     * @param page
     * @return
     */
    public IPage<BannerWebVO> findPage(BannerQuery bannerQuery, Page<Banner> page) {
        return ConversionBeanUtils.conversionBean(page(page, new QueryWrapper<Banner>().lambda()
                .eq(Banner::getBannerType, bannerQuery.getBannerType())
                .eq(Banner::getSchoolId, bannerQuery.getSchoolId())
                .eq(Banner::getDisplay, true)
        ), this::toBannerWebVO);
    }

}
