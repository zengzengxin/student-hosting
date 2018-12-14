package com.luwei.controller;

import com.luwei.common.exception.ValidationException;
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
@RestController
@RequestMapping("/api/weChat")
@Slf4j
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

    @GetMapping("/authorize")
    @ApiOperation("微信公众号授权接口")
    public String userAuthorize(@RequestParam("code") String code, @RequestParam("state") String state) {
        log.info("---------code：{}-----------", code);
        log.info("---------state：{}-----------", state);

        //log.info("前端url:{}", state);
        String url = weChatService.authorize(code, state);
        log.info("最终url:{}", url);
        return url;
    }

}
