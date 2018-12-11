package com.luwei.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Set;

/**
 * @author luwei
 **/
@RestController
@Api(tags = "模板模块")
@RequestMapping("/api/template")
public class TemplateController {

    @PostMapping
    @ApiOperation("添加")
    public Object save(@RequestBody @Valid Object object) {
        return null;
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> objectIds) {
    }

    @GetMapping
    @ApiOperation("去编辑")
    public Object toEdit() {
        return null;
    }

    @PutMapping
    @ApiOperation("修改")
    public Object update(@RequestBody Object object) {
        return null;
    }


    @GetMapping("page")
    @ApiOperation("分页")
    public Page<Object> page(@ModelAttribute Object dto, @PageableDefault(sort = "objectId", direction = Sort.Direction.DESC) Pageable pageable) {
        return null;
    }

    @GetMapping("export")
    @ApiOperation("导出excel")
    public void export(@RequestBody Object query, HttpServletResponse response) {

    }

}
