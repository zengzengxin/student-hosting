package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.course.Course;
import com.luwei.model.course.pojo.cms.CourseAddDTO;
import com.luwei.model.course.pojo.cms.CourseQueryDTO;
import com.luwei.model.course.pojo.cms.CourseUpdateDTO;
import com.luwei.model.course.pojo.cms.CourseVO;
import com.luwei.service.course.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    public CourseVO saveCourse(@RequestBody @Valid CourseAddDTO addDTO) {
        return courseService.saveCourse(addDTO);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void deleteCourses(@RequestParam @ApiParam("courseId列表") Set<Integer> courseIds) {
        courseService.deleteCourses(courseIds);
    }

    @PutMapping
    @ApiOperation("修改")
    public CourseVO updateCourse(@RequestBody @Valid CourseUpdateDTO updateDTO) {
        return courseService.updateCourse(updateDTO);
    }

    @GetMapping
    @ApiOperation("查询详情")
    public CourseVO getCourse(@RequestParam @ApiParam("courseId") Integer courseId) {
        return courseService.getCourse(courseId);
    }

    @GetMapping("/page")
    @ApiOperation("分页获取")
    public IPage<CourseVO> page(@ModelAttribute CourseQueryDTO queryDTO, Page<Course> page) {
        return courseService.findPage(queryDTO, page);
    }

}