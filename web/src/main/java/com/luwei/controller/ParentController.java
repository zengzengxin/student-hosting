package com.luwei.controller;

import com.luwei.model.child.pojo.cms.ChildVO;
import com.luwei.model.parent.pojo.web.ParentAddDTO;
import com.luwei.model.parent.pojo.web.ParentUpdateDTO;
import com.luwei.model.parent.pojo.web.ParentVO;
import com.luwei.service.parent.ParentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ffq
 * @since 2018-12-12
 */
@Api(tags = {"家长模块"})
@RestController
@RequestMapping("/api/parent")
public class ParentController {
    @Autowired
    private ParentService parentService;

    @ApiIgnore
    @GetMapping
    @ApiOperation("查询家长详情")
    public ParentVO findById() {
        // Integer parentId = UserHelper.getUserId();
        Integer parentId = 1;
        return parentService.findParentById(parentId);
    }


    //finish
    @PostMapping
    @ApiOperation("添加")
    public ParentVO save(@RequestBody @Valid ParentAddDTO parentAddDTO) {
        return parentService.saveParent(parentAddDTO);
    }

/*    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        parentService.deleteParents(ids);
    }*/


    //finish
    @PutMapping
    @ApiOperation("修改")
    public ParentVO update(@RequestBody @Valid ParentUpdateDTO parentUpdateDTO) {
        return parentService.updateParent(parentUpdateDTO);
    }





/*    @GetMapping("page")
    @ApiOperation("分页")
    public IPage<ParentVO> page(@ModelAttribute ParentQueryDTO parentQueryDTO, Page page) {
        return parentService.findParentPage(parentQueryDTO, page);
    }*/


    @GetMapping("childList")
    @ApiOperation("根据家长的id查找他的孩子的集合")
    public List<ChildVO> childList(@RequestParam @ApiParam("parentId") Integer id) {
        return parentService.findAllParentById(id);
    }
}

