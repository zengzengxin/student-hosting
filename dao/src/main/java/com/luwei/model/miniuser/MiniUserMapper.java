package com.luwei.model.miniuser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffq
 * @since 2018-12-20
 */
public interface MiniUserMapper extends BaseMapper<MiniUser> {

    MiniUser findUserByOpenId(@Param("openId") String openId);
}
