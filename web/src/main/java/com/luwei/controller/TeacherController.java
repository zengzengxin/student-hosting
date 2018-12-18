package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luwei.model.teacher.pojo.cms.TeacherVO;
import com.luwei.model.teacher.pojo.web.TeacherQueryDTO;
import com.luwei.model.teacher.pojo.web.TeacherUpdateDTO;
import com.luwei.service.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * Author: huanglp
 * Date: 2018-12-18
 */
@Api(tags = "小程序教师模块")
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
            
    @Resource
    private TeacherService teacherService;

    @DeleteMapping
    @ApiOperation("删除")
    public void deleteTeachers(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        teacherService.deleteTeachers(ids);
    }

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

    /*@GetMapping("/page")
    @ApiOperation("分页获取")
    public IPage<TeacherVO> page(@ModelAttribute @Valid TeacherQueryDTO queryDTO, Page<Teacher> page) {
        return teacherService.findPage(queryDTO, page);
    }*/

    @GetMapping("/courseList")
    @ApiOperation("列表")
    public IPage<TeacherVO> list(@ModelAttribute @Valid TeacherQueryDTO queryDTO) {
        return teacherService.courseList(queryDTO);
    }

}
