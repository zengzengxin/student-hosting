package com.luwei.controller;

import com.luwei.model.teacher.pojo.cms.TeacherVO;
import com.luwei.model.teacher.pojo.web.TeacherUpdateDTO;
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

    @PutMapping
    @ApiOperation("修改")
    public TeacherVO updateTeacher(@RequestBody @Valid TeacherUpdateDTO updateDTO) {
        return teacherService.updateTeacher(updateDTO);
    }

    @GetMapping
    @ApiOperation("查询详情")
    public TeacherVO getTeacher(@RequestParam @ApiParam("id") Integer id) {
        return teacherService.getTeacher(id);
    }

}
