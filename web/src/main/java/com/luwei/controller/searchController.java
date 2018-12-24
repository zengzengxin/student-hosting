package com.luwei.controller;

import com.luwei.model.search.SearchVO;
import com.luwei.service.search.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class searchController {

    @Resource
    SearchService searchService;



    @GetMapping("List")
    @ApiOperation("返回所有学校")
    public List<SearchVO> schoolList() {
        List<SearchVO> sever = searchService.findSever();
        return sever;
    }
}
