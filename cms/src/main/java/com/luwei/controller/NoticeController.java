package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.common.constant.RoleConstant;
import com.luwei.model.notice.Notice;
import com.luwei.model.notice.pojo.cms.NoticeAddDTO;
import com.luwei.model.notice.pojo.cms.NoticeCmsVO;
import com.luwei.model.notice.pojo.cms.NoticeQueryDTO;
import com.luwei.model.notice.pojo.cms.NoticeUpdateDTO;
import com.luwei.service.notice.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zzx
 * @since 2018-12-05
 */
@Api(tags = "公告模块")
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @PostMapping
    @ApiOperation("添加")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN,RoleConstant.OPERATOR})
    public NoticeCmsVO save(@RequestBody @Valid NoticeAddDTO notice) {
        return noticeService.saveNotice(notice);
    }

    @DeleteMapping
    @ApiOperation("删除")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN,RoleConstant.OPERATOR})
    public void delete(@RequestParam @ApiParam("+") Integer ids) {
        noticeService.deleteNotice(ids);

    }

    @PutMapping
    @ApiOperation("修改")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN,RoleConstant.OPERATOR})
    public NoticeCmsVO update(@RequestBody NoticeUpdateDTO noticeUpdateDTO) {
        return noticeService.updateNotice(noticeUpdateDTO);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN,RoleConstant.OPERATOR})
    public IPage<NoticeCmsVO> page(@ModelAttribute NoticeQueryDTO noticeQueryDTO, Page<Notice> page) {
        return noticeService.getNoticePage(page, noticeQueryDTO);
    }
}

