package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.common.constant.RoleConstant;
import com.luwei.common.util.QiniuUtil;
import com.luwei.common.util.RadomUtil;
import com.luwei.model.course.Course;
import com.luwei.model.course.pojo.cms.*;
import com.luwei.service.course.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Api(tags = "课程模块" )
@RestController
@RequestMapping("/api/course" )
public class CourseController {

    @PostMapping("qiniu")
    public String uploadImgQiniu( MultipartFile file) throws IOException {
        String key = RadomUtil.getNumRandom(10);
        FileInputStream inputStream = (FileInputStream) file.getInputStream();
        String path = QiniuUtil.uploadImg(inputStream, key);
        System.out.println(path);
        return path;
    }


    @Resource
    private CourseService courseService;

    @PostMapping
    @ApiOperation("新增" )
    public CourseCmsVO saveCourse(@RequestBody @Valid CourseAddDTO courseAddDTO) {
        return courseService.saveCourse(courseAddDTO);
    }

    @PostMapping("add")
    @ApiOperation("新增" )
    public boolean saveCourse(@RequestBody @Valid CourseAddDTOS courseAddDTOS) {
        return courseService.addCourse(courseAddDTOS);
    }

    @DeleteMapping
    @ApiOperation("删除" )
    public void deleteCourses(@RequestParam @ApiParam("courseId列表" ) Set<Integer> courseIds) {
        courseService.deleteCourses(courseIds);
    }

    @GetMapping("delete")
    public boolean deleteCourse(@RequestParam("id") Integer id){
        Set<Integer> courseIds = new TreeSet<>();
        courseIds.add(id);
        courseService.deleteCourses(courseIds);
        return  true;
    }

    @PutMapping
    @ApiOperation("修改" )
    public CourseCmsVO updateCourse(@RequestBody @Valid CourseUpdateDTO courseUpdateDTO) {
        return courseService.updateCourse(courseUpdateDTO);
    }

    @PostMapping("update")
    @ApiOperation("修改" )
    public CourseCmsVO updateCourses(@RequestBody @Valid CourseUPDTO courseUPDTO) {
        return courseService.update(courseUPDTO);
    }

    @GetMapping("getCourse")
    @ApiOperation("查询详情" )
    public CourseCmsVO getCourse(@RequestParam("courseId") @ApiParam("courseId" ) Integer courseId) {
        return courseService.getCourse(courseId);
    }

    @GetMapping("/page" )
    @ApiOperation("分页获取" )
    public IPage<CourseCmsVO> page(@ModelAttribute CourseQueryDTO courseQueryDTO, Page<Course> page) {
        return courseService.findPage(courseQueryDTO, page);
    }

    @GetMapping("/findAll" )
    public List<CourseCmsVOS> findAll() {
        return courseService.findAll();
    }



    @PutMapping("/recommend" )
    @ApiOperation("设为推荐" )
    public CourseCmsVO recommend(@RequestBody @Valid CourseRecommend recommend) {
        return courseService.recommend(recommend);
    }

    @PutMapping("/display" )
    @ApiOperation("上下架" )
    public CourseCmsVO display(@RequestBody @Valid CourseDisplay courseDisplay) {
        return courseService.display(courseDisplay);
    }

}
