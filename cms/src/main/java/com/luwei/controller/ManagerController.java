package com.luwei.controller;

import com.luwei.common.constants.RoleConstant;
import com.luwei.model.manager.pojo.*;
import com.luwei.service.manager.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @ApiOperation("添加")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public ManagerPageVO add(@RequestBody @Valid ManagerAddVO addVO) {
        return managerService.add(addVO);
    }

    @DeleteMapping
    @ApiOperation("删除")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> managerIds) {
        managerService.delete(managerIds);
    }

    @GetMapping("page")
    @ApiOperation("分页列表")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public Page<ManagerPageVO> findPage(@ModelAttribute ManagerQueryVO managerQueryVO,
                                        @PageableDefault(sort = "role", direction = Sort.Direction.ASC) Pageable pageable) {
        return managerService.findPage(managerQueryVO, pageable);
    }

    @PutMapping("password")
    @ApiOperation("重置密码")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public ManagerPageVO resetPassword(@RequestBody @Valid ManagerResetPasswordVO managerResetPasswordVO) {
        return managerService.resetPassword(managerResetPasswordVO);
    }

    @PutMapping("state")
    @ApiOperation("禁用|开启")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public ManagerPageVO handleDisabled(@RequestBody @Valid ManagerStateVO managerStateVO) {
        return managerService.handleDisabled(managerStateVO);
    }

    @PutMapping("role")
    @ApiOperation("禁用|开启")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public ManagerPageVO handleRole(@RequestBody @Valid ManagerRoleVO managerRoleVO) {
        return managerService.handleRole(managerRoleVO);
    }

}
