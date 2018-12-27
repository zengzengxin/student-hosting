package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.hosting.Hosting;
import com.luwei.model.hosting.pojo.cms.*;
import com.luwei.service.hosting.HostingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * @author zzx
 * @since 2018-12-17
 */
@Api(tags = {"托管模块"})
@RestController
@RequestMapping("/api/hosting")
public class HostingController {
    @Autowired
    private HostingService hostingService;

    @PostMapping
    @ApiOperation("添加")
    public HostingCmsVO save(@RequestBody @Valid HostingAddDTO hostingAddDTO) {
        return hostingService.saveHosting(hostingAddDTO);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("hostingId列表") Set<Integer> hostingIds) {
        hostingService.deleteHostings(hostingIds);
    }

    @PutMapping
    @ApiOperation("修改")
    public HostingCmsVO update(@RequestBody @Valid HostingUpdateDTO hostingUpdateDTO) {
        return hostingService.updateHosting(hostingUpdateDTO);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    public IPage<HostingCmsVO> page(@ModelAttribute HostingQueryDTO hostingQueryDTO, Page<Hosting> page) {
        return hostingService.findHostingPage(hostingQueryDTO, page);
    }

    @PutMapping("/recommend")
    @ApiOperation("设为推荐")
    public HostingCmsVO recommend(@RequestBody @Valid HostingRecommend recommend) {
        return hostingService.recommend(recommend);
    }

}

