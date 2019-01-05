package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.common.constant.RoleConstant;
import com.luwei.model.banner.Banner;
import com.luwei.model.banner.pojo.cms.BannerAddDTO;
import com.luwei.model.banner.pojo.cms.BannerCmsVO;
import com.luwei.model.banner.pojo.cms.BannerQueryDTO;
import com.luwei.model.banner.pojo.cms.BannerUpdateDTO;
import com.luwei.model.search.SearchCmsVO;
import com.luwei.service.banner.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

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

    @PostMapping
    @ApiOperation("新增")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public BannerCmsVO saveBanner(@RequestBody @Valid BannerAddDTO bannerAddDTO) {
        return bannerService.saveBanner(bannerAddDTO);
    }

    @DeleteMapping
    @ApiOperation("删除")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public void deleteBanners(@RequestParam @ApiParam("bannerId列表") Set<Integer> ids) {
        bannerService.deleteBanners(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public BannerCmsVO updateBanner(@RequestBody @Valid BannerUpdateDTO bannerUpdateDTO) {
        return bannerService.updateBanner(bannerUpdateDTO);
    }

    @GetMapping
    @ApiOperation("查询详情")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public BannerCmsVO getBanner(@RequestParam @ApiParam("bannerId") Integer bannerId) {
        return bannerService.getBanner(bannerId);
    }

    @GetMapping("/page")
    @ApiOperation("分页获取")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public IPage<BannerCmsVO> page(@ModelAttribute @Valid BannerQueryDTO bannerQueryDTO, Page<Banner> page) {
        return bannerService.findPage(bannerQueryDTO, page);
    }

    @GetMapping("/course")
    @ApiOperation("返回所有课程")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public List<SearchCmsVO> listCourses() {
        return bannerService.listCourses();
    }

    @GetMapping("/hosting")
    @ApiOperation("返回所有托管")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public List<SearchCmsVO> listHosting() {
        return bannerService.listHosting();
    }

}
