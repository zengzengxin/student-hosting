package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.hosting.Hosting;
import com.luwei.model.hosting.pojo.web.HostingQuery;
import com.luwei.model.hosting.pojo.web.HostingWebVO;
import com.luwei.service.hosting.HostingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zzx
 * @since 2018-12-17
 */
@Api(tags = "托管模块")
@RestController
@RequestMapping("/api/hosting")
public class HostingController {

    @Resource
    private HostingService hostingService;

    @GetMapping
    @ApiOperation("查询详情")
    public HostingWebVO findById(@RequestParam @ApiParam("hostingId") Integer hostingId) {
        return hostingService.findById(hostingId);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    public IPage<HostingWebVO> page(@ModelAttribute HostingQuery hostingQuery, Page<Hosting> page) {
        return hostingService.findHostingPage(hostingQuery, page);
    }

}

