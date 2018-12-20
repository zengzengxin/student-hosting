package com.luwei.controller;

import com.luwei.model.course.pojo.mini.MyCourseQuery;
import com.luwei.model.course.pojo.mini.MyCourseVO;
import com.luwei.service.course.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Api(tags = "小程序课程模块")
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @GetMapping
    @ApiOperation("查询详情")
    public MyCourseVO getMyCourse(@RequestParam @ApiParam("coursePackageId") Integer coursePackageId) {
        return courseService.getMyCourse(coursePackageId);
    }

    @GetMapping("/list")
    @ApiOperation("查询我的课程")
    public List<MyCourseVO> listMyCourse(@ModelAttribute MyCourseQuery query) {
        return courseService.listMyCourse(query);
    }

}
