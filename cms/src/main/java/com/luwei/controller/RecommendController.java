package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.recommend.Recommend;
import com.luwei.model.recommend.pojo.cms.RecommendQueryDTO;
import com.luwei.model.recommend.pojo.cms.RecommendVO;
import com.luwei.service.recommend.RecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

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

    /*
    @PostMapping
    @ApiOperation("新增")
    public RecommendVO saveRecommend(@RequestBody @Valid RecommendAddDTO addDTO) {
        return recommendService.saveRecommend(addDTO);
    }
    */

    @DeleteMapping
    @ApiOperation("删除")
    public void deleteRecommends(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        recommendService.deleteRecommends(ids);
    }

 /*   @PutMapping
    @ApiOperation("修改")
    public RecommendVO updateRecommend(@RequestBody @Valid RecommendUpdateDTO updateDTO) {
        return recommendService.updateRecommend(updateDTO);
    }*/

    @GetMapping
    @ApiOperation("查询详情")
    public RecommendVO getRecommend(@RequestParam @ApiParam("id") Integer id) {
        return recommendService.getRecommend(id);
    }

    @GetMapping("/page")
    @ApiOperation("分页获取")
    public IPage<RecommendVO> page(@ModelAttribute @Valid RecommendQueryDTO queryDTO, Page<Recommend> page) {
        return recommendService.findPage(queryDTO, page);
    }

}
