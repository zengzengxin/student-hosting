package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.parent.pojo.cms.ParentQueryDTO;
import com.luwei.model.parent.pojo.cms.ParentCmsVO;
import com.luwei.service.parent.ParentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author huanglp
 * Date: 2018-12-12
 */
@Api(tags = {"家长模块"})
@RestController
@RequestMapping("/api/parent")
public class ParentController {

    @Autowired
    private ParentService parentService;


    @GetMapping("page")
    @ApiOperation("分页")
    public IPage<ParentCmsVO> page(@ModelAttribute ParentQueryDTO parentQueryDTO , Page page) {
        return parentService.getParentPage(parentQueryDTO, page);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("+") Integer ids) {
        parentService.deleteParent(ids);

    }
}
