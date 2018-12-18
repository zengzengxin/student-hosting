package com.luwei.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.hosting.pojo.cms.HostingAddDTO;
import com.luwei.model.hosting.pojo.cms.HostingQueryDTO;
import com.luwei.model.hosting.pojo.cms.HostingUpdateDTO;
import com.luwei.model.hosting.pojo.cms.HostingVO;
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
@Api(tags = {""})
@RestController
@RequestMapping("/api/hosting")
    public class HostingController {
        @Autowired
    private HostingService hostingService;


    @GetMapping
    @ApiOperation("查询详情")
    public HostingVO findById(@RequestParam @ApiParam("hostingId") Integer hostingId) {
        return hostingService.findById(hostingId);
    }


    @PostMapping
    @ApiOperation("添加")
    public HostingVO save(@RequestBody @Valid HostingAddDTO hostingAddDTO) {
    return hostingService.saveHosting(hostingAddDTO);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("hostingId列表") Set<Integer> hostingIds) {
        hostingService.deleteHostings(hostingIds);
    }

    @PutMapping
    @ApiOperation("修改")
    public HostingVO update(@RequestBody @Valid HostingUpdateDTO hostingUpdateDTO) {
         return hostingService.updateHosting(hostingUpdateDTO);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    public IPage<HostingVO> page(@ModelAttribute HostingQueryDTO hostingQueryDTO, Page page) {
        return hostingService.findHostingPage(hostingQueryDTO,page);
    }
}

