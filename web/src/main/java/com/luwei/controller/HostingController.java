package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.hosting.pojo.web.HostingWebVO;
import com.luwei.service.hosting.HostingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    @ApiOperation("查询详情")
    public HostingWebVO findById(@RequestParam @ApiParam("hostingId") Integer hostingId) {
        return hostingService.findById(hostingId);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    public IPage<HostingWebVO> page(Page page) {
        return hostingService.findHostingPage( page);
    }

}

