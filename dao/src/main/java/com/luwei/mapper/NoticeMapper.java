package com.luwei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luwei.model.notice.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffq
 * @since 2018-12-05
 */
@Mapper
@Repository
public interface NoticeMapper extends BaseMapper<Notice> {

}
