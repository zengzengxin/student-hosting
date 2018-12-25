package com.luwei.controller;

import com.luwei.model.teacher.Teacher;
import com.luwei.model.teacher.pojo.mini.TeacherMiniVO;
import com.luwei.model.teacher.pojo.web.TeacherUpdateDTO;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.miniuser.MiniUserService;
import com.luwei.service.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Author: huanglp
 * Date: 2018-12-19
 */
@Api(tags = "小程序教师模块")
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @Resource
    private MiniUserService miniUserService;

    @PutMapping
    @ApiOperation("修改")
    public TeacherMiniVO updateTeacher(@RequestBody @Valid TeacherUpdateDTO updateDTO) {
        return teacherService.updateTeacher(updateDTO);
    }

    @GetMapping
    @ApiOperation("查询详情")
    public TeacherMiniVO getTeacher() {
        return teacherService.getTeacher();
    }


    @GetMapping("/checkout")
    @ApiOperation("判断微信用户是否绑定孩子,返回true或false")
    public boolean wechatUserBingding() {
        Integer userId = UserHelper.getUserId();
        if (miniUserService.getById(userId).getTeacherId() == 0){
            return false;
        }else {
            return true;
        }
    }


    @GetMapping("/phone")
    @ApiOperation("绑定手机号")
    public Teacher bingTeacher(@RequestParam @ApiParam("phone") String phone ){
        Integer userId = UserHelper.getUserId();
        return teacherService.bindingTeacher(phone,userId);
    }







}
