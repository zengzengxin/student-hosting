package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.common.constant.RoleConstant;
import com.luwei.model.course.Course;
import com.luwei.model.course.pojo.cms.*;
import com.luwei.service.course.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Api(tags = "课程模块")
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @PostMapping
    @ApiOperation("新增")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.OPERATOR})
    public CourseCmsVO saveCourse(@RequestBody @Valid CourseAddDTO courseAddDTO) {
        return courseService.saveCourse(courseAddDTO);
    }

    @DeleteMapping
    @ApiOperation("删除")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.OPERATOR})
    public void deleteCourses(@RequestParam @ApiParam("courseId列表") Set<Integer> courseIds) {
        courseService.deleteCourses(courseIds);
    }

    @PutMapping
    @ApiOperation("修改")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.OPERATOR})
    public CourseCmsVO updateCourse(@RequestBody @Valid CourseUpdateDTO courseUpdateDTO) {
        return courseService.updateCourse(courseUpdateDTO);
    }

    @GetMapping
    @ApiOperation("查询详情")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ADMIN,RoleConstant.ROOT,RoleConstant.OPERATOR})
    public CourseCmsVO getCourse(@RequestParam @ApiParam("courseId") Integer courseId) {
        return courseService.getCourse(courseId);
    }

    @GetMapping("/page")
    @ApiOperation("分页获取")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ADMIN,RoleConstant.ROOT,RoleConstant.OPERATOR})
    public IPage<CourseCmsVO> page(@ModelAttribute CourseQueryDTO courseQueryDTO, Page<Course> page) {
        return courseService.findPage(courseQueryDTO, page);
    }

    @PutMapping("/recommend")
    @ApiOperation("设为推荐")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public CourseCmsVO recommend(@RequestBody @Valid CourseRecommend recommend) {
        return courseService.recommend(recommend);
    }

    @PutMapping("/display")
    @ApiOperation("上下架")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public CourseCmsVO display(@RequestBody @Valid CourseDisplay courseDisplay) {
        return courseService.display(courseDisplay);
    }

}
