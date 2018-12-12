package com.luwei.service.notice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.notice.Notice;
import com.luwei.model.notice.NoticeMapper;
import com.luwei.model.notice.pojo.cms.NoticeQueryDTO;
import com.luwei.model.notice.pojo.cms.NoticeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ffq
 * @since 2018-12-05
 */
@Slf4j
@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> {


    private NoticeVO toNoticeVO(Notice notice) {
        NoticeVO noticeVO = new NoticeVO();
        BeanUtils.copyNonNullProperties(notice,noticeVO);
        return  noticeVO;
    }

   public  NoticeVO findById(Integer id) {
        // 若此id已被逻辑删除,也会返回null
        Notice notice = getById(id);
        Assert.notNull(notice, MessageCodes.NOTICE_IS_NOT_EXIST);
        return toNoticeVO(notice);
    }




    public IPage<NoticeVO> getNoticePage( Page<Notice> page,NoticeQueryDTO noticePageDTO) {
        return baseMapper.getNoticePage(page,noticePageDTO);
        //TODO---这个方法没有测
       /* Notice notice = new Notice();
        BeanUtils.copyNonNullProperties(noticePageDTO, notice);
        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<Notice>();
       // if(noticePageDTO.)
        noticeQueryWrapper.lambda().between(Notice::getCreateTime,noticePageDTO.getStartTime(), noticePageDTO.getEndTime())
                .like(Notice::getNoticeTittle,noticePageDTO.getNoticeTittle())
                .eq(Notice::getNoticeStatus,noticePageDTO.getNoticeStatus());
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page,noticeQueryWrapper), this::toNoticeVO);*/
    }

}
