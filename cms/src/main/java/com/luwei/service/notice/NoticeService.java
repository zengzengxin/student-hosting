package com.luwei.service.notice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.util.BeanUtils;
import com.luwei.model.notice.Notice;
import com.luwei.model.notice.NoticeMapper;
import com.luwei.model.notice.pojo.NoticeAddDTO;
import com.luwei.model.notice.pojo.NoticeQueryDTO;
import com.luwei.model.notice.pojo.NoticeUpdateDTO;
import com.luwei.model.notice.pojo.NoticeVO;
import com.luwei.utils.ConversionBeanUtils;
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
@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> {

    @Transactional
    public NoticeVO saveNotice(NoticeAddDTO noticeAddDTO){
        Notice notice= new Notice();
        BeanUtils.copyNonNullProperties(noticeAddDTO,notice);

        //添加一些没有的参数
        notice.setCreateTime(LocalDateTime.now());
        notice.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(notice);
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
        return toNoticeVO(notice);
    }



    public void deleteNotice(Integer id){
        System.out.println(id);
        baseMapper.deleteById(id);
    }


    public IPage<NoticeVO> getNoticePage(NoticeQueryDTO noticePageDTO, Page<Notice> page) {
        //TODO---这个方法没有测
        Notice notice = new Notice();
        BeanUtils.copyNonNullProperties(noticePageDTO, notice);
        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<Notice>(notice);
        noticeQueryWrapper.between("create_time", noticePageDTO.getStartTime(), noticePageDTO.getEndTime());
        return ConversionBeanUtils.conversionBean(baseMapper.selectPage(page, noticeQueryWrapper), this::toNoticeVO);
    }

}
