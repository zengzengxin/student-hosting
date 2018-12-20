package com.luwei.service.wechat;

import com.alibaba.fastjson.JSONObject;
import com.luwei.common.exception.MessageCodes;
import com.luwei.common.property.WxProperties;
import com.luwei.model.miniuser.MiniUser;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.service.miniuser.MiniUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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


    public String addMinuuser(JSONObject jsonObject){
        MiniUser miniUser = new MiniUser();
        miniUser.setOpenId(jsonObject.get("openId").toString());
        miniUser.setAvatarUrl(jsonObject.get("avatarUrl").toString());
        miniUser.setGender((Integer) jsonObject.get("gender"));
        miniUser.setNickName(jsonObject.get("nickName").toString());
        LocalDateTime time = LocalDateTime.now();
        miniUser.setUpdateTime(time).setCreateTime(time).setDeleted(false);
        boolean flag = miniUserService.save(miniUser);
        Assert.isTrue(flag, MessageCodes.MINIUSER_SAVE_ERROR);


        String token = shiroTokenService.login(miniUser.getMiniUserId().toString());

        return token;
    }

}
