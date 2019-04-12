package com.luwei.model.notice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.notice.pojo.cms.NoticeCmsVO;
import com.luwei.model.notice.pojo.cms.NoticeQueryDTO;
import org.apache.ibatis.annotations.Param;

/**
 * @author zzx
 * @since 2018-12-05
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    IPage<NoticeCmsVO> getNoticePage(@Param("page" ) Page<Notice> page, @Param("noticePageDTO" ) NoticeQueryDTO noticePageDTO,
                                     @Param("type" ) Integer type, @Param("schoolId" ) Integer schoolId);

}
