package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.common.constant.RoleConstant;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.course.pojo.cms.CourseAddDTOS;
import com.luwei.model.course.pojo.cms.CourseCmsVOS;
import com.luwei.model.course.pojo.cms.CourseUPDTO;
import com.luwei.model.hosting.Hosting;
import com.luwei.model.hosting.pojo.cms.*;
import com.luwei.service.hosting.HostingService;
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
import java.util.TreeSet;

/**
 * @author zzx
 * @since 2018-12-17
 */
@Api(tags = {"托管模块"})
@RestController
@RequestMapping("/api/hosting" )
public class HostingController {

    @Resource
    private HostingService hostingService;

    @PostMapping
    @ApiOperation("添加" )
    public HostingCmsVO save(@RequestBody @Valid HostingAddDTO hostingAddDTO) {
        return hostingService.saveHosting(hostingAddDTO);
    }

    @PostMapping("add")
    @ApiOperation("新增" )
    public boolean saveHosting(@RequestBody @Valid HostingAddDTOS hostingAddDTOS) {
        return hostingService.saveHosting(hostingAddDTOS);
    }

    @DeleteMapping
    @ApiOperation("删除" )
    public void delete(@RequestParam @ApiParam("hostingId列表" ) Set<Integer> hostingIds) {
        hostingService.deleteHostings(hostingIds);
    }


    @GetMapping("delete")
    public boolean deleteCourse(@RequestParam("id") Integer id){
        Set<Integer> hostingIds = new TreeSet<>();
        hostingIds.add(id);
        hostingService.deleteHostings(hostingIds);
        return  true;
    }

    @PutMapping
    @ApiOperation("修改" )
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.OPERATOR})
    public HostingCmsVO update(@RequestBody @Valid HostingUpdateDTO hostingUpdateDTO) {
        return hostingService.updateHosting(hostingUpdateDTO);

    }

    @PostMapping("update")
    @ApiOperation("修改" )
    public boolean updateCourses(@RequestBody @Valid HostingUPDTO hostingUPDTO) {
        return hostingService.updateHostings(hostingUPDTO);

    }

    @GetMapping("page" )
    @ApiOperation("分页" )
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ADMIN, RoleConstant.ROOT, RoleConstant.OPERATOR})
    public IPage<HostingCmsVO> page(@ModelAttribute HostingQueryDTO hostingQueryDTO, Page<Hosting> page) {
        return hostingService.findHostingPage(hostingQueryDTO, page);
    }

    @PutMapping("/recommend" )
    @ApiOperation("设为推荐" )
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public HostingCmsVO recommend(@RequestBody @Valid HostingRecommend recommend) {
        return hostingService.recommend(recommend);
    }

    @PutMapping("/display" )
    @ApiOperation("上下架" )
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public HostingCmsVO display(@RequestBody @Valid HostingDisplay hostingDisplay) {
        return hostingService.display(hostingDisplay);
    }


    @GetMapping("/findAll" )
    public List<HostingVO> findAll() {
        return hostingService.findAll();
    }

    @GetMapping("getHosting")
    @ApiOperation("查询详情" )
    public HostingCmsVOS getHosting(@RequestParam("hostingId") @ApiParam("hostingId" ) Integer hostingId) {
        Hosting hosting = hostingService.getById(hostingId);
        System.out.println(hosting);
        HostingCmsVOS hostingCmsVOS = new HostingCmsVOS();
        BeanUtils.copyNonNullProperties(hosting,hostingCmsVOS);
        System.out.println(hostingCmsVOS);
        return  hostingCmsVOS;
    }

}

