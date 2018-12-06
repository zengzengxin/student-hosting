package com.luwei.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.banner.pojo.cms.BannerAddDTO;
import com.luwei.model.banner.pojo.cms.BannerUpdateDTO;
import com.luwei.model.banner.pojo.cms.BannerVO;
import com.luwei.service.banner.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author huanglp
 * @since 2018-12-06
 */
@Api(tags = "战队接口")
@RestController
@RequestMapping("/api/banner")
public class BannerController {
    @Resource
    private BannerService bannerService;

    @GetMapping
    @ApiOperation("查询单条")
    public Object findOne(@ModelAttribute @Valid Object object) {
        return null;
    }

    @PostMapping
    @ApiOperation("添加")
    public BannerVO saveBanner(@RequestBody @Valid BannerAddDTO addDTO) {
        return bannerService.saveBanner(addDTO);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void deleteBanner(@RequestParam @ApiParam("id") Integer id) {
        bannerService.deleteBanner(id);
    }

    @PutMapping
    @ApiOperation("修改")
    public BannerVO updateBanner(@RequestBody BannerUpdateDTO updateDTO) {
        return bannerService.updateBanner(updateDTO);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<BannerVO> page(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Page page) {
        return bannerService.getPage(page);
    }
}

