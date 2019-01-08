package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.common.constant.RoleConstant;
import com.luwei.common.exception.ValidationException;
import com.luwei.model.school.pojo.cms.SchoolCmsVO;
import com.luwei.model.school.pojo.cms.SchoolQueryDTO;
import com.luwei.service.school.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author zzx
 * @since 2018-12-13
 */
@Api(tags = "学校管理")
@RestController
@RequestMapping("/api/school")
public class SchoolController {

    @Resource
    private SchoolService schoolService;

    @GetMapping
    @ApiOperation("查询详情根据id")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public SchoolCmsVO findById(@RequestParam @ApiParam("schoolId") Integer schoolId) {
        return schoolService.findById(schoolId);
    }

    @DeleteMapping
    @ApiOperation("删除")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public void delete(@RequestParam @ApiParam("schoolId列表") Set<Integer> schoolIds) {
        schoolService.deleteSchools(schoolIds);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public IPage<SchoolCmsVO> page(@ModelAttribute SchoolQueryDTO schoolQueryDTO, Page page) {
        return schoolService.findSchoolPage(schoolQueryDTO, page);
    }

    @GetMapping("/list")
    @ApiOperation("返回所有学校")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN, RoleConstant.OPERATOR})
    public List<SchoolCmsVO> schoolList() {
        return schoolService.findSchoolPage();
    }

    @PostMapping("/import")
    @ApiOperation("导入Excel")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public String importExcel(MultipartFile file) {
        Boolean result = schoolService.readExcelFile(file);
        if (result) {
            return "上传成功";
        }
        throw new ValidationException("上传失败");
    }

}

