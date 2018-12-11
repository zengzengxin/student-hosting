package com.luwei.controller;

import com.luwei.model.child.pojo.cms.ChildAddDTO;
import com.luwei.model.child.pojo.cms.ChildUpdateDTO;
import com.luwei.model.child.pojo.cms.ChildVO;
import com.luwei.service.child.ChildService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ffq
 * @since 2018-12-11
 */
@Api(tags = {""})
@RestController
@RequestMapping("/api/child")
public class ChildController {
    @Autowired
    private ChildService childService;

    //通过父母的id查询他的孩子
    @GetMapping
    @ApiOperation("查询详情")
    public List<ChildVO> findById(@RequestParam @ApiParam("id") Integer id) {
        return childService.findById(id);
    }

    //finish
    @PostMapping
    @ApiOperation("添加")
    public ChildVO save(@RequestBody @Valid ChildAddDTO childAddDTO) {
        return childService.saveChild(childAddDTO);
    }


    @PutMapping
    @ApiOperation("修改")
    public ChildVO update(@RequestBody @Valid ChildUpdateDTO childUpdateDTO) {
        return childService.updateChild(childUpdateDTO);
    }


}

