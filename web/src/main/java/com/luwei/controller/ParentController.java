package com.luwei.controller;

import com.luwei.model.child.pojo.web.ChildWebVO;
import com.luwei.model.parent.Parent;
import com.luwei.model.parent.pojo.web.ParentAddDTO;
import com.luwei.model.parent.pojo.web.ParentEditDTO;
import com.luwei.model.parent.pojo.web.ParentLoginDTO;
import com.luwei.model.parent.pojo.web.ParentWebVO;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.parent.ParentService;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author zzx
 * @since 2018-12-12
 */

@RestController
@RequestMapping("/api/parent")
public class ParentController {


    @Resource
    private ParentService parentService;

    @PostMapping("login")
    public ParentWebVO login(@RequestBody ParentLoginDTO parentLoginDTO) {
        return parentService.login(parentLoginDTO);
    }


    @GetMapping("findParentById")
    public ParentWebVO findById(@Param("id") Integer id) {
        //Integer parentId = UserHelper.getUserId();
        return parentService.findParentById(id);
    }

    @PutMapping("update")
    public ParentWebVO update(@RequestBody @Valid ParentEditDTO parentUpdateDTO) {
        return parentService.updateParent(parentUpdateDTO);
    }

    @GetMapping("")
    @ApiOperation("查找家长的孩子列表")
    public List<ChildWebVO> childList() {
        return parentService.findAllParentById(UserHelper.getUserId());
    }

    @PostMapping("save")
    public boolean save(@RequestBody @Valid ParentAddDTO parentAddDTO) {
        parentService.save(parentAddDTO);
        return true;
    }
}

