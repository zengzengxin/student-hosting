package com.luwei.model.miniuser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffq
 * @since 2018-12-20
 */
public interface MiniUserMapper extends BaseMapper<MiniUser> {

    MiniUser findUserByOpenId(String openId);
}
