package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.banner.Banner;
import com.luwei.model.banner.pojo.cms.BannerQueryDTO;
import com.luwei.model.banner.pojo.web.BannerWebVO;
import com.luwei.service.banner.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Api(tags = "轮播图模块")
@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Resource
    private BannerService bannerService;

    @GetMapping("/page")
    @ApiOperation("分页获取")
    public IPage<BannerWebVO> page(@ModelAttribute @Valid BannerQueryDTO bannerQueryDTO, Page<Banner> page) {
        return bannerService.findPage(bannerQueryDTO, page);
    }

}
