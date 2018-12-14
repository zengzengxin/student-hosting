package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.school.pojo.cms.SchoolQueryDTO;
import com.luwei.model.school.pojo.cms.SchoolVO;
import com.luwei.model.school.pojo.web.SchoolWebVO;
import com.luwei.service.school.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
/**
* @author zzx
* @since 2018-12-13
*/
@Api(tags = {"机构管理"})
@RestController
@RequestMapping("/api/school")
    public class SchoolController {
        @Autowired
    private SchoolService schoolService;


    @GetMapping
    @ApiOperation("查询详情根据id")
    public SchoolVO findById(@RequestParam @ApiParam("schoolId") Integer schoolId) {
        return schoolService.findById(schoolId);
    }


/*    @PostMapping
    @ApiOperation("添加")
    public SchoolVO save(@RequestBody @Valid SchoolAddDTO schoolAddDTO) {
    return schoolService.saveSchool(schoolAddDTO);
    }*/

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("schoolId列表") Set<Integer> schoolIds) {
        schoolService.deleteSchools(schoolIds);
    }
/*
    @PutMapping
    @ApiOperation("修改")
    public SchoolVO update(@RequestBody @Valid SchoolUpdateDTO schoolUpdateDTO) {
         return schoolService.updateSchool(schoolUpdateDTO);
    }*/

    @GetMapping("page")
    @ApiOperation("分页")
    public IPage<SchoolVO> page(@ModelAttribute SchoolQueryDTO schoolQueryDTO, Page page) {
        return schoolService.findSchoolPage(schoolQueryDTO,page);
    }

    @GetMapping("schoolList")
    @ApiOperation("返回所有学校")
    public List<SchoolWebVO> schoolList() {
        return schoolService.findSchoolPage();
    }


}

