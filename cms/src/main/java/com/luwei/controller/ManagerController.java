package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.common.constant.RoleConstant;
import com.luwei.model.manager.pojo.*;
import com.luwei.service.manager.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * @author luwei
 **/
@RestController
@Api(tags = "管理员模块")
@RequestMapping("/api/manager")
public class ManagerController {

    @Resource
    private ManagerService managerService;

    @PostMapping
    @ApiOperation("添加管理员")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public ManagerPageVO addManager(@RequestBody @Valid ManagerAddVO addVO) {

        return managerService.add(addVO, addVO.getRole());
    }

    @DeleteMapping
    @ApiOperation("删除管理员")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public void deleteManager(@RequestParam @ApiParam("id列表") Set<Integer> managerIds) {
        managerService.delete(managerIds);
    }

    @PutMapping
    @ApiOperation("修改管理员")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public ManagerPageVO updateManager(@RequestBody @Valid ManagerEditVO editVO) {
        return managerService.update(editVO);
    }

    @GetMapping("page")
    @ApiOperation("分页列表")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public IPage<ManagerPageVO> findPage(@ModelAttribute ManagerQueryVO managerQueryVO,
                                         @PageableDefault(sort = "role", direction = Sort.Direction.ASC) Page pageable) {
        return managerService.findPage(managerQueryVO, pageable);
    }

    @PutMapping("password")
    @ApiOperation("重置密码")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public ManagerPageVO resetPassword(@RequestBody @Valid ManagerResetPasswordVO managerResetPasswordVO) {
        return managerService.resetPassword(managerResetPasswordVO);
    }

    @PutMapping("state")
    @ApiOperation("禁用|开启")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public ManagerPageVO handleDisabled(@RequestBody @Valid ManagerStateVO managerStateVO) {
        return managerService.handleDisabled(managerStateVO);
    }

    @PutMapping("/school")
    @ApiOperation("绑定学校")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public ManagerPageVO bindingSchool(@RequestBody @Valid ManagerBindingSchool bindingSchool) {
        return managerService.bindingSchool(bindingSchool);
    }

}
