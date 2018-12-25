package com.luwei.controller;

import com.luwei.model.search.SearchWebVO;
import com.luwei.service.search.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zzx
 * Date: 2018-12-24
 */
@Api(tags = "搜索模块")
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @GetMapping("list")
    @ApiOperation("返回所有课程/托管")
    public List<SearchWebVO> schoolList(@RequestParam(required = false) @ApiParam("name") String name) {
        return searchService.findSever(name);
    }
}
