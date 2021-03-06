package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.course.Course;
import com.luwei.model.course.pojo.web.CourseQuery;
import com.luwei.model.course.pojo.web.CourseWebVO;
import com.luwei.model.course.pojo.web.SimpleCourseVO;
import com.luwei.model.hosting.pojo.web.HostingWebVO;
import com.luwei.service.course.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Api(tags = "课程模块" )
@RestController
@RequestMapping("/api/course" )
public class CourseController {

    @Resource
    private CourseService courseService;

    @GetMapping("getCourse")
    @ApiOperation("查询详情" )
    public CourseWebVO getCourse(@RequestParam("courseId") @ApiParam("courseId" ) Integer id) {
        return courseService.getCourse(id);
    }

    @GetMapping("/page" )
    @ApiOperation("分页获取" )
    public IPage<SimpleCourseVO> page(@ModelAttribute @Valid CourseQuery courseQuery, Page<Course> page) {
        return courseService.findPage(courseQuery, page);
    }

    @GetMapping("/getCourses" )
    @ApiOperation("分页获取" )
    public List<CourseWebVO> page(@RequestParam("start") Integer start) {
        return courseService.getCourses(start);
    }

}
