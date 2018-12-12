package com.luwei.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.notice.Notice;
import com.luwei.model.notice.pojo.web.NoticeAddDTO;
import com.luwei.model.notice.pojo.web.NoticeQueryDTO;
import com.luwei.model.notice.pojo.web.NoticeUpdateDTO;
import com.luwei.model.notice.pojo.web.NoticeVO;
import com.luwei.service.notice.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author ffq
 * @since 2018-12-05
 */
@Api(tags = {"公告模块"})
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Resource
    private NoticeService iNoticeService;

    @PostMapping
    @ApiOperation("添加")
    public NoticeVO save(@RequestBody @Valid NoticeAddDTO notice) {
        return iNoticeService.saveNotice(notice);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("+") Integer ids) {
        iNoticeService.deleteNotice(ids);

    }

    @PutMapping
    @ApiOperation("修改")
    public NoticeVO update(@RequestBody NoticeUpdateDTO noticeUpdateDTO) {
        return iNoticeService.updateNotice(noticeUpdateDTO);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public IPage<NoticeVO> page(@ModelAttribute NoticeQueryDTO noticeQueryDTO,Page<Notice> page) {
        return iNoticeService.getNoticePage(page,noticeQueryDTO);
    }
}

