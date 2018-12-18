package com.luwei.common.util;

import com.luwei.common.holder.SpringBeanHolder;
import com.luwei.common.property.WxMiniProperties;
import com.luwei.common.property.WxProperties;

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
        //if (miniProperties == null) {
        //    miniProperties = SpringBeanHolder.getBean(WxMiniProperties.class);
        //}
        if (wxProperties == null) {
            wxProperties = SpringBeanHolder.getBean(WxProperties.class);
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
