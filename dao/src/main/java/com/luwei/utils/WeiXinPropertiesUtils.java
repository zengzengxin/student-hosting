package com.luwei.utils;

import com.luwei.model.wechat.WxMiniProperties;
import com.luwei.model.wechat.WxProperties;
import org.springframework.web.context.ContextLoader;

/**
 * Author: huanglp
 * Date: 2018-11-28
 */
public class WeiXinPropertiesUtils {

    // 微信小程序配置
    private static WxMiniProperties miniProperties;
    // 微信公众号配置
    private static WxProperties wxProperties;

    private static void init() {
        if (miniProperties == null) {
            miniProperties = ContextLoader.getCurrentWebApplicationContext().getBean(WxMiniProperties.class);
        }
        if (wxProperties == null) {
            wxProperties = ContextLoader.getCurrentWebApplicationContext().getBean(WxProperties.class);
        }
    }

    public static WxMiniProperties getWxMiniProperties() {
        init();
        return miniProperties;
    }

    public static WxProperties getWxProperties() {
        init();
        return wxProperties;
    }
}
