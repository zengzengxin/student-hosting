package com.luwei.controller;

import com.luwei.common.exception.ValidationException;
import com.luwei.common.property.WeChatUser;
import com.luwei.common.util.WeChatUtils;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.wechat.WeChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Author: huanglp
 * Date: 2018-12-12
 */
@Api(tags = "微信模块")
@RequestMapping("/api/weChat")
@Slf4j
@Controller
public class WeChatController {

    @Resource
    private WeChatService weChatService;

    @Resource
    private WeChatUtils weChatUtils;

    @GetMapping("/verify")
    @ApiOperation("校验token是否可用")
    @ResponseBody
    public Boolean userAuthorize() {
        try {
            Integer userId = UserHelper.getUserId();
        } catch (Exception e) {
            throw new ValidationException("token已过期");
        }
        return true;
    }

    @GetMapping("/authorize")
    @ApiOperation("微信公众号授权接口")
    public String userAuthorize(@RequestParam("code") String code, @RequestParam("state") String state) {
        log.info("---------code：{}-----------", code);
        log.info("---------state：{}-----------", state);

        Map<String, Object> authorizeMap = weChatUtils.authorize(code);
        log.info("------authorizeMap: {}-------", authorizeMap.toString());
        Map<String, Object> userInfoMap = weChatUtils.getUserInfo((String) authorizeMap.get("access_token"),
                (String) authorizeMap.get("openId"));
        log.info("------userInfo: {}-----------", userInfoMap.toString());
        String openId = (String) userInfoMap.get("openId");
        if (openId == null || "".equals(openId)) {
            throw new ValidationException("授权失败");
        }
        String headImgURL = (String) userInfoMap.get("headimgurl");
        String nickName = (String) userInfoMap.get("nickname");
        Integer gender = (Integer) userInfoMap.get("sex");
        //WeChatUser weChatUser = new WeChatUser(openId, nickName, gender, headImgURL);
        WeChatUser weChatUser = new WeChatUser();
        weChatUser.setOpenId(openId).setNickname(nickName).setGender(gender).setAvatar(headImgURL);
        String url = weChatService.getWeChatUserInfo(weChatUser, state);
        log.info("最终url:{}", url);
        return url;

        //log.info("前端url:{}", state);
        //String url = weChatService.authorize(code, state);
        //log.info("最终url:{}", url);
        //return url;
    }

}
