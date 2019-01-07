package com.luwei.service.notice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.common.util.ConversionBeanUtils;
import com.luwei.model.notice.Notice;
import com.luwei.model.notice.NoticeMapper;
import com.luwei.model.notice.pojo.web.NoticeWebVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author zzx
 * @since 2018-12-05
 */
@Slf4j
@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> {

    private NoticeWebVO toNoticeVO(Notice notice) {
        NoticeWebVO noticeVO = new NoticeWebVO();
        BeanUtils.copyNonNullProperties(notice, noticeVO);
        return noticeVO;
    }

    public NoticeWebVO findById(Integer id) {
        // 若此id已被逻辑删除,也会返回null
        Notice notice = getById(id);
        Assert.notNull(notice, MessageCodes.NOTICE_IS_NOT_EXIST);
        return toNoticeVO(notice);
    }

    public IPage<NoticeWebVO> getNoticePage(Page<Notice> page) {
        return ConversionBeanUtils.conversionBean(page(page, new QueryWrapper<Notice>().lambda()
                .eq(Notice::getDisplay, true)
        ), this::toNoticeVO);
    }

}
