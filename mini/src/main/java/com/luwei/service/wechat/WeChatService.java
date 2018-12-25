package com.luwei.service.wechat;

import com.luwei.common.exception.MessageCodes;
import com.luwei.common.exception.ValidationException;
import com.luwei.common.property.WxProperties;
import com.luwei.model.miniuser.MiniUser;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.service.miniuser.MiniUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Author: huanglp
 * Date: 2018-12-14
 */
@Service
@Slf4j
public class WeChatService {

    @Resource
    private MiniUserService miniUserService;

    @Resource
    private ShiroTokenService shiroTokenService;

    @Resource
    private WxProperties wxProperties;

    public String miniAuthorize() {

        Integer userId = 1;
        shiroTokenService.logout(userId.toString());
        return shiroTokenService.login(userId.toString());
    }


    public String addMinuuser(Map<String, Object> map){
        MiniUser miniUser;
        //判断是否已经授权
        String openId = map.get("openId").toString();
        if (openId == null || "".equals(openId)) {
            throw new ValidationException("授权失败");
        }
         miniUser = miniUserService.findUserByOpenId(openId);
        if(miniUser == null) {
            miniUser = new MiniUser();
            miniUser.setOpenId(map.get("openId").toString());
            miniUser.setAvatarUrl(map.get("avatarUrl").toString());
            miniUser.setGender((Integer) map.get("gender"));
            miniUser.setNickName(map.get("nickName").toString());
            LocalDateTime time = LocalDateTime.now();
            miniUser.setUpdateTime(time).setCreateTime(time).setDeleted(false);
            boolean flag = miniUserService.save(miniUser);
            Assert.isTrue(flag, MessageCodes.MINIUSER_SAVE_ERROR);
        }

        //不为空直接返回
        String token = shiroTokenService.login(miniUser.getMiniUserId().toString());

        return token;
    }

}
