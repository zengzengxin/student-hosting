package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.course.Course;
import com.luwei.model.course.pojo.cms.CourseQueryDTO;
import com.luwei.model.course.pojo.cms.CourseVO;
import com.luwei.model.course.pojo.web.CourseWebVO;
import com.luwei.service.course.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @GetMapping
    @ApiOperation("查询详情")
    public CourseVO getCourse(@RequestParam @ApiParam("courseId") Integer id) {
        return courseService.getCourse(id);
    }

    @GetMapping("/page")
    @ApiOperation("分页获取")
    public IPage<CourseWebVO> page(@ModelAttribute CourseQueryDTO queryDTO, Page<Course> page) {
        return courseService.findPage(queryDTO, page);
    }

}
