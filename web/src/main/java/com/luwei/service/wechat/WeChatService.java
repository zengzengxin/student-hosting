package com.luwei.service.wechat;

import com.luwei.common.property.WeChatUser;
import com.luwei.common.property.WxProperties;
import com.luwei.model.parent.Parent;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.service.parent.ParentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ParentService parentService;

    @Resource
    private ShiroTokenService shiroTokenService;

    @Resource
    private WxProperties wxProperties;

    @Transactional
    public String getWeChatUserInfo(WeChatUser weChatUser, String state) {
        Parent parent = parentService.findByOpenid(weChatUser.getOpenId());
        log.info("用户的昵称：{}", weChatUser.getNickname());
        //判断是否为新用户
        if (parent == null) {
            parent = new Parent();

            parent.setOpenId(weChatUser.getOpenId());
            parent.setNickName(weChatUser.getNickname());
            parent.setGender(weChatUser.getGender());
            parent.setAvatarUrl(weChatUser.getAvatar());

            LocalDateTime time = LocalDateTime.now();
            parent.setUpdateTime(time);
            parent.setCreateTime(time);
            parentService.save(parent);
            log.info("保存数据---:{}", parent);
        }
        Integer userId = parent.getParentId();
        shiroTokenService.logout(userId.toString());
        String token = shiroTokenService.login(userId.toString());
        log.info("生成token: {}, userId: {}", token, userId.toString());
        return "redirect:" + state + "?token=" + token;
    }

}
