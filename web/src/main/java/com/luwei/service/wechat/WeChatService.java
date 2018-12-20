package com.luwei.service.wechat;

import com.luwei.common.property.WxProperties;
import com.luwei.common.util.WeiXinUtils;
import com.luwei.model.parent.Parent;
import com.luwei.model.user.pojo.WeChatUser;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.service.parent.ParentService;
import com.riversoft.weixin.common.oauth2.OpenUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    /**
     * 微信公众号网页授权
     *
     * @param code
     * @param state
     * @return
     */
    public String authorize(String code, String state) {
        //调用工具类
        OpenUser openUser = WeiXinUtils.webSiteLogin(code);

        Parent parent = parentService.findByOpenid(openUser.getOpenId());
        log.info("用户的昵称：{}", openUser.getNickName());
        //判断是否为新用户
        if (parent == null) {
            parent = new Parent();

            parent.setOpenid(openUser.getOpenId());
            parent.setNickName(openUser.getNickName());
            parent.setGender(openUser.getSex().getCode());
            parent.setAvatarUrl(openUser.getHeadImgUrl());

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

    public String getWeChatUserInfo(WeChatUser weChatUser, String state) {
        Parent parent = parentService.findByOpenid(weChatUser.getOpenId());
        log.info("用户的昵称：{}", weChatUser.getNickname());
        //判断是否为新用户
        if (parent == null) {
            parent = new Parent();

            parent.setOpenid(weChatUser.getOpenId());
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
