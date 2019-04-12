package com.luwei.controller;

import com.luwei.model.teacher.Teacher;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * @author luwei
 **/
@RestController
@Api(tags = "模板模块" )
@RequestMapping("api/template" )
public class TemplateController {

    @GetMapping
    @ApiOperation("查询单条" )
    public Teacher findOne(@ModelAttribute @Valid Object object) {
        Teacher teacher = new Teacher().setBinding(true);
        return teacher;
    }


    @PostMapping
    @ApiOperation("添加" )
    public void save(@RequestBody @Valid Object object) {
    }

    @DeleteMapping
    @ApiOperation("删除" )
    public void delete(@RequestParam @ApiParam("id列表" ) Set<Integer> ids) {
    }

    @PutMapping
    @ApiOperation("修改" )
    public Object update(@RequestBody Object object) {
        return null;
    }

    @GetMapping("/page" )
    @ApiOperation("分页" )
    public Page<Object> page(@ModelAttribute Object dto, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return null;
    }

}
