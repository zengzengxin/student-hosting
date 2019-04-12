package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.course.pojo.web.CourseWebVO;
import com.luwei.model.recommend.Recommend;
import com.luwei.model.recommend.pojo.web.RecommendWebVO;
import com.luwei.model.recommend.pojo.web.RecommendWebDTO;
import com.luwei.service.recommend.RecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-20
 */
@Api(tags = "推荐服务模块" )
@RestController
@RequestMapping("/api/recommend" )
public class RecommendController {

    @Resource
    private RecommendService recommendService;

    @GetMapping("/page" )
    @ApiOperation("分页获取" )
    public IPage<RecommendWebVO> page(@ModelAttribute @Valid RecommendWebDTO queryDTO, Page<Recommend> page) {
        return recommendService.findPage(queryDTO, page);
    }


    @GetMapping("findBySeverId")
    @ApiOperation("查询详情" )
    public Recommend getCourse(@RequestParam("severId") @ApiParam("severId" ) Integer id, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return recommendService.findBySeverId(id);
    }



    @GetMapping("/pages" )
    @ApiOperation("分页获取" )
    public List<RecommendWebVO> page() {
        return recommendService.findPages();
    }
}
