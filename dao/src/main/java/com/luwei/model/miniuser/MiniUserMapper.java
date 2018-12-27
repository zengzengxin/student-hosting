package com.luwei.model.miniuser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zzx
 * @since 2018-12-20
 */
public interface MiniUserMapper extends BaseMapper<MiniUser> {

    MiniUser findUserByOpenId(@Param("openId") String openId);
}
