package com.luwei.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.service.notice.NoticeService;
import com.luwei.service.notice.pojos.NoticeAddDTO;
import com.luwei.service.notice.pojos.NoticeQueryDTO;
import com.luwei.service.notice.pojos.NoticeUpdateDTO;
import com.luwei.service.notice.pojos.NoticeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ffq
 * @since 2018-12-05
 */
@Api(tags = {""})
@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    @Autowired
    private NoticeService iNoticeService;

    @PostMapping
    @ApiOperation("添加")
    public NoticeVO save(@RequestBody @Valid NoticeAddDTO notice) {
        return iNoticeService.saveNotice(notice);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id") Integer ids) {
        iNoticeService.deleteNotice(ids);

    }

    @PutMapping
    @ApiOperation("修改")
    public NoticeVO update(@RequestBody NoticeUpdateDTO noticeUpdateDTO) {
        return iNoticeService.updateNotice(noticeUpdateDTO);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public IPage<NoticeVO> page(@ModelAttribute NoticeQueryDTO noticeQueryDTO, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Page page) {
        return iNoticeService.getNoticePage(noticeQueryDTO,page);
    }
}

