package com.luwei.controller;

import com.luwei.common.exception.MessageCodes;
import com.luwei.common.exception.ValidationException;
import com.luwei.common.util.WeiXinUtils;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.service.miniuser.MiniUserService;
import com.luwei.service.wechat.WeChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Author: huanglp
 * Date: 2018-12-12
 */
@Api(tags = "微信模块" )
@RequestMapping("/api/weChat" )
@Slf4j
@RestController
public class WeChatController {

    @Resource
    private WeChatService weChatService;

    @Resource
    private MiniUserService miniUserService;

    @Resource
    private ShiroTokenService shiroTokenService;

    @GetMapping("/verify" )
    @ApiOperation("校验token是否可用" )
    public Boolean userAuthorize() {
        try {
            Integer userId = UserHelper.getUserId();
        } catch (Exception e) {
            throw new ValidationException(MessageCodes.AUTH_TOKEN);
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
    @GetMapping("/login" )
    @ApiOperation("小程序授权接口" )
    public String miniAuthorize(@RequestParam String encryptedData, @RequestParam String iv, @RequestParam String code) {

        String sessionKey = (String) WeiXinUtils.login(code).get("session_key" );
        Map<String, Object> map = WeiXinUtils.decryptWxData(encryptedData, sessionKey, iv);

        return weChatService.addMiniUser(map);
    }

}
