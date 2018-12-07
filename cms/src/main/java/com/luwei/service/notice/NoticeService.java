package com.luwei.service.notice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.notice.Notice;
import com.luwei.model.notice.NoticeMapper;
import com.luwei.model.notice.pojo.cms.NoticeAddDTO;
import com.luwei.model.notice.pojo.cms.NoticeQueryDTO;
import com.luwei.model.notice.pojo.cms.NoticeUpdateDTO;
import com.luwei.model.notice.pojo.cms.NoticeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

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

    @Transactional
    public NoticeVO saveNotice(NoticeAddDTO noticeAddDTO){
        Notice notice= new Notice();
        BeanUtils.copyNonNullProperties(noticeAddDTO,notice);

        //添加一些没有的参数
        notice.setCreateTime(LocalDateTime.now());
        notice.setUpdateTime(LocalDateTime.now());
        boolean flag = saveOrUpdate(notice);
        Assert.isTrue(flag,MessageCodes.NOTICE_SAVE_ERROR);
        log.info("----添加一条公告----");
        return toNoticeVO(notice);
    }

    private NoticeVO toNoticeVO(Notice notice) {
        NoticeVO noticeVO = new NoticeVO();
        BeanUtils.copyNonNullProperties(notice,noticeVO);
        return  noticeVO;
    }




    @Transactional
    public NoticeVO updateNotice(NoticeUpdateDTO noticeUpdateDTO){
        Notice notice= new Notice();
        BeanUtils.copyNonNullProperties(noticeUpdateDTO,notice);
        //添加一些没有的参数
        notice.setUpdateTime(LocalDateTime.now());
        // saveOrUpdate(notice);
        Boolean flag = updateById(notice);
        Assert.isTrue(flag, MessageCodes.NOTICE_UPDATE_ERROR);
        log.info("----更新一条公告----");
        return toNoticeVO(notice);
    }



    @Transactional
    public void deleteNotice(Integer id){
        baseMapper.deleteById(id);
        boolean flag = removeById(id);
        Assert.isTrue(flag, MessageCodes.NOTICE_DELETE_ERROR);
        log.info("----删除一条公告----");
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
