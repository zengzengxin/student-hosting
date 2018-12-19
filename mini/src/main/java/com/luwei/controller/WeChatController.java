package com.luwei.controller;

import com.luwei.common.exception.ValidationException;
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
    public String userAuthorize(@RequestParam String encryptedData, @RequestParam String iv, @RequestParam String code) {

        // TODO 小程序授权

        return null;
    }

}
