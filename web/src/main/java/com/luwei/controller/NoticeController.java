package com.luwei.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ffq
 * @since 2018-12-05
 */
@Api(tags = {"公告模块"})
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    //@Resource
    //private NoticeService iNoticeService;


/*    @GetMapping
    @ApiOperation("通过id查询一条公告")
    public ParentVO findById(@RequestParam @ApiParam("noticeId") Integer id) {
        return NoticeService.getNotice(id);
    }*/


    /*@GetMapping("/page")
    @ApiOperation("分页")
    public IPage<NoticeVO> page(@ModelAttribute NoticeQueryDTO noticeQueryDTO,Page<Notice> page) {
        return iNoticeService.getNoticePage(page,noticeQueryDTO);
    }*/
}

