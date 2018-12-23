package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.recommend.Recommend;
import com.luwei.model.recommend.pojo.web.RecommendWebVO;
import com.luwei.model.recommend.pojo.web.RecommendWebDTO;
import com.luwei.service.recommend.RecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Author: huanglp
 * Date: 2018-12-20
 */
@Api(tags = "推荐服务模块")
@RestController
@RequestMapping("/api/recommend")
public class RecommendController {

    @Resource
    private RecommendService recommendService;

    @GetMapping("/page")
    @ApiOperation("分页获取")
    public IPage<RecommendWebVO> page(@ModelAttribute @Valid RecommendWebDTO queryDTO, Page<Recommend> page) {
        return recommendService.findPage(queryDTO, page);
    }

}
