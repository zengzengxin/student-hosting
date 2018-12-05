package com.luwei.controllers;

import com.luwei.service.manager.pojos.ManagerPageVO;
import com.luwei.service.personal.center.PersonalCenterService;
import com.luwei.service.personal.center.pojos.PersonalCenterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(tags = "个人中心模块")
@RequestMapping("/api/personalCenter")
public class PersonalCenterController {

    @Resource
    private PersonalCenterService personalCenterService;

    @GetMapping
    @ApiOperation("获取个人信息")
    public ManagerPageVO toEdit() {
        return personalCenterService.toEdit();
    }

    @PutMapping
    @ApiOperation("修改个人信息")
    public ManagerPageVO update(@RequestBody @Valid PersonalCenterVO personalCenterVO) {
        return personalCenterService.update(personalCenterVO);
    }

}
