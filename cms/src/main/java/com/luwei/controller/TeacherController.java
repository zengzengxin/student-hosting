package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.common.constant.RoleConstant;
import com.luwei.model.teacher.pojo.cms.TeacherAddDTO;
import com.luwei.model.teacher.pojo.cms.TeacherCmsVO;
import com.luwei.model.teacher.pojo.cms.TeacherQueryDTO;
import com.luwei.model.teacher.pojo.cms.TeacherUpdateDTO;
import com.luwei.service.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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

    @Resource
    private TeacherService teacherService;

    @GetMapping
    @ApiOperation("查询老师详情（根据id）")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ADMIN})
    public TeacherCmsVO findById(@RequestParam @ApiParam("teacherId") Integer teacherId) {
        return teacherService.findById(teacherId);
    }

    @PostMapping
    @ApiOperation("添加")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ADMIN})
    public TeacherCmsVO save(@RequestBody @Valid TeacherAddDTO teacherAddDTO) {
        return teacherService.saveTeacher(teacherAddDTO);
    }

    @DeleteMapping
    @ApiOperation("删除")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ADMIN})
    public void delete(@RequestParam @ApiParam("teacherId列表") Set<Integer> teacherIds) {
        teacherService.deleteTeachers(teacherIds);
    }

    @PutMapping
    @ApiOperation("修改")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ADMIN})
    public TeacherCmsVO update(@RequestBody @Valid TeacherUpdateDTO teacherUpdateDTO) {
        return teacherService.updateTeacher(teacherUpdateDTO);
    }

    @GetMapping("page")
    @ApiOperation("分页")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ADMIN})
    public IPage<TeacherCmsVO> page(@ModelAttribute TeacherQueryDTO teacherQueryDTO, Page page) {
        return teacherService.findTeacherPage(teacherQueryDTO, page);
    }

    @GetMapping("List")
    @ApiOperation("根据学校id查询所有的老师")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ADMIN})
    public List<TeacherCmsVO> teacherList(@RequestParam @ApiParam("schoolId") Integer schoolId) {
        return teacherService.teacherList(schoolId);
    }

    @GetMapping("Teacher")
    @ApiOperation("根据学校id和老师的名字查询老师")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ADMIN})
    public List<TeacherCmsVO> findTeacher(@RequestParam(required = false) @ApiParam("schoolId") Integer schoolId, @RequestParam(required = false) @ApiParam("teacherName") String teacherName) {
        return teacherService.findTeacher(schoolId, teacherName);
    }

    @PostMapping("excelAddTeacher")
    @ApiOperation("通过excel导入老师")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ADMIN})
    public void findTeacher(MultipartFile file) throws Exception {
        teacherService.importExcel(file);
    }

}

