package com.luwei.controllers;

import com.luwei.service.notice.INoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * @author ffq
 * @since 2018-12-05
 */
@Api(tags = {""})
@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    @Autowired
    private INoticeService iNoticeService;

    @GetMapping
    @ApiOperation("查询单条")
    public Object findOne(@ModelAttribute @Valid Object object) {
        return null;
    }

    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid Object object) {
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
    }

    @PutMapping
    @ApiOperation("修改")
    public Object update(@RequestBody Object object) {
        return null;
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<Object> page(@ModelAttribute Object dto, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return null;
    }
}

