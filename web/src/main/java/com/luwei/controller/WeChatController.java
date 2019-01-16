package com.luwei.controller;

import com.luwei.common.exception.MessageCodes;
import com.luwei.common.exception.ValidationException;
import com.luwei.common.property.ShareParam;
import com.luwei.common.property.WeChatUser;
import com.luwei.common.util.WeChatUtils;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.parent.ParentService;
import com.luwei.service.wechat.WeChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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

    @Resource
    private ParentService parentService;

    @GetMapping("/verify")
    @ApiOperation("校验token是否可用")
    @ResponseBody
    public Boolean userAuthorize() {
        Integer userId;
        try {
            userId = UserHelper.getUserId();
            Assert.notNull(parentService.getById(userId), MessageCodes.AUTH_TOKEN);
        } catch (Exception e) {
            log.info("token过期，返回false");
            return false;
        }
        log.info("token校验通过，未过期，userId:{}", userId);
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
                (String) authorizeMap.get("openid"));
        log.info("------userInfo: {}-----------", userInfoMap.toString());
        String openId = (String) userInfoMap.get("openid");
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
    }

    @GetMapping(value = "getShareParam")
    @ApiOperation("获取js调用信息")
    @ResponseBody
    public ShareParam getShareParam(@RequestParam @ApiParam(value = "当前页面的url") String shareUrl) {
        ShareParam shareParam = weChatUtils.sign(shareUrl);
        System.out.println("=====" + shareParam);
        return shareParam;
    }

}
