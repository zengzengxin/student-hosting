package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.banner.pojo.cms.BannerAddDTO;
import com.luwei.model.banner.pojo.cms.BannerQueryDTO;
import com.luwei.model.banner.pojo.cms.BannerUpdateDTO;
import com.luwei.model.banner.pojo.cms.BannerVO;
import com.luwei.service.banner.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author huanglp
 * @since 2018-12-06
 */
@Api(tags = "轮播图模块")
@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Resource
    private BannerService bannerService;

    @PostMapping
    @ApiOperation("新增轮播图")
    public BannerVO saveBanner(@RequestBody @Valid BannerAddDTO addDTO) {
        return bannerService.saveBanner(addDTO);
    }

    @DeleteMapping
    @ApiOperation("删除轮播图")
    public void deleteBanner(@RequestParam Integer id) {
        bannerService.deleteBanner(id);
    }

    @PutMapping
    @ApiOperation("修改轮播图")
    public BannerVO updateBanner(@RequestBody BannerUpdateDTO updateDTO) {
        return bannerService.updateBanner(updateDTO);
    }

    @GetMapping("/page")
    @ApiOperation("分页获取轮播图")
    public IPage<BannerVO> page(@ModelAttribute @Valid BannerQueryDTO queryDTO, Page page) {
        return bannerService.getPage(queryDTO, page);
    }

}

