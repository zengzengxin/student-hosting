package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.common.constant.RoleConstant;
import com.luwei.model.parent.pojo.cms.ParentCmsVO;
import com.luwei.model.parent.pojo.cms.ParentQueryDTO;
import com.luwei.service.parent.ParentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author huanglp
 * Date: 2018-12-12
 */
@Api(tags = {"家长模块"})
@RestController
@RequestMapping("/api/parent")
public class ParentController {

    @Resource
    private ParentService parentService;

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public IPage<ParentCmsVO> page(@ModelAttribute ParentQueryDTO parentQueryDTO, Page page) {
        return parentService.getParentPage(parentQueryDTO, page);
    }

    @DeleteMapping
    @ApiOperation("删除")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public void delete(@RequestParam @ApiParam("+") Integer ids) {
        parentService.deleteParent(ids);

    }
}
