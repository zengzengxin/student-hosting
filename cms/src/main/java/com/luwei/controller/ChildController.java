package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.child.Child;
import com.luwei.model.child.pojo.cms.ChildAddDTO;
import com.luwei.model.child.pojo.cms.ChildQueryDTO;
import com.luwei.model.child.pojo.cms.ChildUpdateDTO;
import com.luwei.model.child.pojo.cms.ChildCmsVO;
import com.luwei.service.child.ChildService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Set;

/**
 * @author zzx
 * @since 2018-12-11
 */
@Api(tags = "学生模块")
@RestController
@RequestMapping("/api/child")
public class ChildController {
    @Autowired
    private ChildService childService;

    @PostMapping
    @ApiOperation("添加孩子/学生")
    public ChildCmsVO save(@RequestBody @Valid ChildAddDTO childAddDTO) {

        return childService.saveChild(childAddDTO);
    }

    @PutMapping
    @ApiOperation("修改孩子/学生")
    public ChildCmsVO update(@RequestBody @Valid ChildUpdateDTO childUpdateDTO) {
        return childService.updateChild(childUpdateDTO);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void deleteChilds(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        childService.deleteChilds(ids);
    }


    @GetMapping("/page")
    @ApiOperation("分页获取")
    public IPage<ChildCmsVO> page(Page<Child> page, @ModelAttribute @Valid ChildQueryDTO childQueryDTO ) {
        return childService.findPage(page,childQueryDTO);
    }


    @PostMapping("excleAddStudent")
    @ApiOperation("通过excle导入学生")
    public void findTeacher(MultipartFile file) throws Exception {
        childService.importExcel(file);
    }
}

