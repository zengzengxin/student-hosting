package com.luwei.model.notice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

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
    public static void main(String[] args) {
        System.out.println(new Date());
    }

}
