package com.luwei.service.notice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.common.constant.RoleEnum;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.manager.Manager;
import com.luwei.model.notice.Notice;
import com.luwei.model.notice.NoticeMapper;
import com.luwei.model.notice.pojo.cms.NoticeAddDTO;
import com.luwei.model.notice.pojo.cms.NoticeCmsVO;
import com.luwei.model.notice.pojo.cms.NoticeQueryDTO;
import com.luwei.model.notice.pojo.cms.NoticeUpdateDTO;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.manager.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author zzx
 * @since 2018-12-05
 */
@Slf4j
@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> {

    @Resource
    private ManagerService managerService;

    @Transactional(rollbackFor = Exception.class)
    public NoticeCmsVO saveNotice(NoticeAddDTO noticeAddDTO) {

        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeAddDTO, notice);

        // 公告类型
        Manager manager = managerService.getById(UserHelper.getUserId());
        RoleEnum role = manager.getRole();
        notice.setType(role.getValue());

        // 添加一些没有的参数
        notice.setCreateTime(LocalDateTime.now());
        notice.setUpdateTime(LocalDateTime.now());
        boolean flag = saveOrUpdate(notice);
        Assert.isTrue(flag, MessageCodes.NOTICE_SAVE_ERROR);
        log.info("----添加一条公告----");
        return toNoticeVO(notice);
    }

    private NoticeCmsVO toNoticeVO(Notice notice) {
        NoticeCmsVO noticeVO = new NoticeCmsVO();
        BeanUtils.copyProperties(notice, noticeVO);
        return noticeVO;
    }

    @Transactional(rollbackFor = Exception.class)
    public NoticeCmsVO updateNotice(NoticeUpdateDTO noticeUpdateDTO) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeUpdateDTO, notice);
        //添加一些没有的参数
        notice.setUpdateTime(LocalDateTime.now());
        Boolean flag = updateById(notice);
        Assert.isTrue(flag, MessageCodes.NOTICE_UPDATE_ERROR);
        log.info("----更新一条公告----");
        return toNoticeVO(baseMapper.selectById(notice));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteNotice(Integer id) {
        boolean flag = removeById(id);
        Assert.isTrue(flag, MessageCodes.NOTICE_DELETE_ERROR);
        log.info("----删除一条公告----");
    }

    public IPage<NoticeCmsVO> getNoticePage(Page<Notice> page, NoticeQueryDTO noticePageDTO) {
        Integer type = managerService.getById(UserHelper.getUserId()).getRole().getValue();
        if (type == 0) {
            type = null;
        }
        return baseMapper.getNoticePage(page, noticePageDTO, type);
    }

}
