package com.luwei.model.notice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luwei.model.notice.pojo.web.NoticeQueryDTO;
import com.luwei.model.notice.pojo.web.NoticeVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffq
 * @since 2018-12-05
 */
public interface NoticeMapper extends BaseMapper<Notice> {

     IPage<NoticeVO> getNoticePage(@Param("page") Page<Notice> page, @Param("noticePageDTO") NoticeQueryDTO noticePageDTO );

}
