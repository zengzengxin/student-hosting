package com.luwei.controller;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.child.pojo.web.ChildBindingDTO;
import com.luwei.model.child.pojo.web.ChildUpdateDTO;
import com.luwei.model.child.pojo.web.ChildWebVO;
import com.luwei.service.child.ChildService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ffq
 * @since 2018-12-11
 */
@Api(tags = {"孩子模块"})
@RestController
@RequestMapping("/api/child")
public class ChildController {
    @Autowired
    private ChildService childService;

    //finish
    @PostMapping
    @ApiOperation("绑定孩子")
    public ChildWebVO save(@RequestBody @Valid ChildBindingDTO childBindingDTO) {
        return childService.bindingChild(childBindingDTO);
    }

    // @ApiIgnore
    @PutMapping
    @ApiOperation("修改孩子")
    public ChildWebVO update(@RequestBody @Valid ChildUpdateDTO childUpdateDTO) {
        return childService.updateChild(childUpdateDTO);
    }

    @GetMapping
    @ApiOperation("判断孩子是否有购买权限")
    public boolean children_buy(@RequestParam @ApiParam("childId") Integer childid, @RequestParam @ApiParam("schoolId") Integer schoolId) {
        ChildWebVO child = childService.findById(childid);
        Assert.isTrue(schoolId != null, MessageCodes.SCHOOLID_IS_NOT_NULL);
        return (schoolId.equals(child.getSchoolId()));
    }

}

