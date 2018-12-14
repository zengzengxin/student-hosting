package com.luwei.controller;

import com.luwei.model.child.pojo.cms.ChildVO;
import com.luwei.model.parent.pojo.web.ParentUpdateDTO;
import com.luwei.model.parent.pojo.web.ParentVO;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.parent.ParentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author zzx
 * @since 2018-12-12
 */
@Api(tags = {"家长模块"})
@RestController
@RequestMapping("/api/parent")
public class ParentController {

    @Resource
    private ParentService parentService;

    @ApiIgnore
    @GetMapping
    @ApiOperation("查询家长详情")
    public ParentVO findById() {
        Integer parentId = UserHelper.getUserId();
        return parentService.findParentById(parentId);
    }

    @PutMapping
    @ApiOperation("修改")
    public ParentVO update(@RequestBody @Valid ParentUpdateDTO parentUpdateDTO) {
        return parentService.updateParent(parentUpdateDTO);
    }

    @GetMapping("childList")
    @ApiOperation("查找家长的孩子列表")
    public List<ChildVO> childList() {
        return parentService.findAllParentById(UserHelper.getUserId());
    }
}

