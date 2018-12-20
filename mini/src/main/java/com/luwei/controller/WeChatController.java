package com.luwei.controller;

import com.alibaba.fastjson.JSONObject;
import com.luwei.common.exception.ValidationException;
import com.luwei.common.util.WeiXinUtils;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.wechat.WeChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Author: huanglp
 * Date: 2018-12-12
 */
@Api(tags = "微信模块")
@RequestMapping("/api/weChat")
@Slf4j
@RestController
public class WeChatController {

    @Resource
    private WeChatService weChatService;

    @GetMapping("/verify")
    @ApiOperation("校验token是否可用")
    public Boolean userAuthorize() {
        try {
            Integer userId = UserHelper.getUserId();
        } catch (Exception e) {
            throw new ValidationException("token已过期");
        }
        return true;
    }

    /**
     * 小程序授权接口
     *
     * @param encryptedData
     * @param iv
     * @param code
     * @return
     */
    @GetMapping("/login")
    @ApiOperation("小程序授权接口")
    public String miniAuthorize(@RequestParam String encryptedData, @RequestParam String iv, @RequestParam String code) {

        // TODO 小程序授权
        String sessionKey = (String) WeiXinUtils.login(code).get("session_key");
        JSONObject jsonObject = WeiXinUtils.decryptWxData(encryptedData, sessionKey, iv);
        System.out.println(jsonObject.toJSONString());
        /* {    "country":"Greenland",
                "watermark":{"appid":"wx27d74f401e89aba2","timestamp":1545281467},
                "gender":1,
                "province":"",
                "city":"",
                "avatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/uib1PuTjleLvfSrusd95Yo3IpqFfScTNrGoPtFM6BLkOx7aibY5ibRHzAhQZOZZGsSUbEAovia3NW0RwWibKekdXBzw/132",
                "openId":"oqpV75bKF5jsrSP27Z-CzV_nySRI",
                "nickName":"小P",
                "language":"zh_CN"
            }
        */
        String token = weChatService.miniAuthorize();
        return null;
    }

}
