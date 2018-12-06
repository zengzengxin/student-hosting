package com.luwei.service.notice;

import com.luwei.mapper.NoticeMapper;
import com.luwei.service.notice.pojos.NoticeDTO;
import com.luwei.service.notice.pojos.NoticeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffq
 * @since 2018-12-05
 */
@Service
public class NoticeServiceImpl {

    @Resource
    private NoticeMapper noticeMapper;

    public NoticeVO SaveNotice(NoticeDTO noticeDTO){
       return null;
       // noticeMapper.insert(noticeDTO);
    }

}
