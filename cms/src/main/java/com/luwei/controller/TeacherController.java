package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.teacher.pojo.cms.TeacherAddDTO;
import com.luwei.model.teacher.pojo.cms.TeacherQueryDTO;
import com.luwei.model.teacher.pojo.cms.TeacherUpdateDTO;
import com.luwei.model.teacher.pojo.cms.TeacherVO;
import com.luwei.service.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
* @author zzx
* @since 2018-12-13
*/
@Api(tags = {"老师管理"})
@RestController
@RequestMapping("/api/teacher")
    public class TeacherController {
        @Autowired
    private TeacherService teacherService;



    @GetMapping
    @ApiOperation("查询老师详情（根据id）")
    public TeacherVO findById(@RequestParam @ApiParam("teacherId") Integer teacherId) {
        return teacherService.findById(teacherId);
    }


    @PostMapping
    @ApiOperation("添加")
    public TeacherVO save(@RequestBody @Valid TeacherAddDTO teacherAddDTO) {
    return teacherService.saveTeacher(teacherAddDTO);
    }


    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("teacherId列表") Set<Integer> teacherIds) {
        teacherService.deleteTeachers(teacherIds);
    }

    @PutMapping
    @ApiOperation("修改")
    public TeacherVO update(@RequestBody @Valid TeacherUpdateDTO teacherUpdateDTO) {
         return teacherService.updateTeacher(teacherUpdateDTO);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    public IPage<TeacherVO> page(@ModelAttribute TeacherQueryDTO teacherQueryDTO, Page page) {
        return teacherService.findTeacherPage(teacherQueryDTO,page);
    }

    @GetMapping("List")
    @ApiOperation("根据学校id查询所有的老师")
    public List<TeacherVO> teacherList(@RequestParam @ApiParam("schoolId") Integer schoolId) {
        return teacherService.teacherList(schoolId);
    }

    @GetMapping("Teacher")
    @ApiOperation("根据学校id和老师的名字查询老师")
    public List<TeacherVO> findTeacher(@RequestParam(required = false) @ApiParam("schoolId") Integer schoolId,@RequestParam(required = false) @ApiParam("teacherName") String teacherName) {
        return teacherService.findTeacher(schoolId,teacherName);
    }


}
