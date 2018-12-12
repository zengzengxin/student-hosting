package com.luwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.notice.Notice;
import com.luwei.model.notice.pojo.cms.NoticeQueryDTO;
import com.luwei.model.notice.pojo.cms.NoticeVO;
import com.luwei.service.notice.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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


/*    @GetMapping
    @ApiOperation("通过id查询一条公告")
    public ParentVO findById(@RequestParam @ApiParam("noticeId") Integer id) {
        return NoticeService.getNotice(id);
    }*/


    @GetMapping("/page")
    @ApiOperation("分页")
    public IPage<NoticeVO> page(@ModelAttribute NoticeQueryDTO noticeQueryDTO,Page<Notice> page) {
        return iNoticeService.getNoticePage(page,noticeQueryDTO);
    }
}

