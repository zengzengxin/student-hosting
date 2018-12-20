package com.luwei.service.wechat;

import com.luwei.common.property.WxProperties;
import com.luwei.module.shiro.service.ShiroTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author: huanglp
 * Date: 2018-12-14
 */
@Service
@Slf4j
public class WeChatService {

    @Resource
    private ShiroTokenService shiroTokenService;

    @Resource
    private WxProperties wxProperties;

    public String miniAuthorize() {

        Integer userId = 1;
        shiroTokenService.logout(userId.toString());
        return shiroTokenService.login(userId.toString());
    }

}
