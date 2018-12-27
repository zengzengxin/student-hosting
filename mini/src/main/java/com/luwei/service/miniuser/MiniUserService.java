package com.luwei.service.miniuser;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.model.miniuser.MiniUser;
import com.luwei.model.miniuser.MiniUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zzx
 * @since 2018-12-20
 */
@Service
@Slf4j
public class MiniUserService extends ServiceImpl<MiniUserMapper, MiniUser> {

    public MiniUser findUserByOpenId(String openId) {
        return baseMapper.findUserByOpenId(openId);
    }

}
