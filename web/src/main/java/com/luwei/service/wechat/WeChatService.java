package com.luwei.service.wechat;

import com.luwei.common.property.WxProperties;
import com.luwei.model.parent.Parent;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.service.parent.ParentService;
import com.riversoft.weixin.common.oauth2.AccessToken;
import com.riversoft.weixin.common.oauth2.OpenUser;
import com.riversoft.weixin.open.base.AppSetting;
import com.riversoft.weixin.open.oauth2.OpenOAuth2s;
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
     * 微信公众号授权
     * @param code
     * @param state
     * @return
     */
    public String authorize(String code, String state) {
        //调用工具类
        log.info("============微信公众号(网页)授权开始===========");
        //WxProperties properties = WeiXinPropertiesUtils.getWxProperties();
        AppSetting appSetting = new AppSetting(wxProperties.getAppId(), wxProperties.getAppSecret());
        OpenOAuth2s openOAuth2s = OpenOAuth2s.with(appSetting);
        AccessToken accessToken = openOAuth2s.getAccessToken(code);

        //获取用户信息
        OpenUser openUser = openOAuth2s.userInfo(accessToken.getAccessToken(), accessToken.getOpenId());
        log.info("============微信公众号(网页)授权结束===========");

        //OpenUser openUser = WeiXinUtils.webSiteLogin(code, state);

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
        log.info("生成token: {}", token);
        return "redirect:" + state + "?token=" + token;
    }

}
