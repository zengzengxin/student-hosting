package com.luwei.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luwei.common.property.WxMiniProperties;
import com.luwei.common.property.WxProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Author: huanglp
 * Date: 2018-11-28
 */
public class WeiXinUtils {

    private static Logger log = LoggerFactory.getLogger(WeiXinUtils.class);

    /**
     * 通过前端传过来的code, 调用小程序登录接口, 获取到message并返回 (包含openid session_key等)
     *
     * @param code
     * @return
     */
    public static Map<String, Object> login(String code) {
        log.info("==============小程序登录方法开始================");
        WxMiniProperties properties = WeiXinPropertiesUtils.getWxMiniProperties();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + properties.getAppId()
                + "&secret=" + properties.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        JSONObject message;
        try {
            // RestTemplate最好做成单例模式
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            message = JSON.parseObject(response);
        } catch (Exception e) {
            log.error("微信服务器请求错误", e);
            message = new JSONObject();
        }
        log.info("message：" + message.toString());
        log.info("==============小程序登录方法结束================");
        return message;

    }

    /**
     * 通过encryptedData,sessionKey,iv获得解密信息, 拥有用户丰富的信息, 包含openid,unionid,昵称等
     */
    public static Map<String, Object> decryptWxData(String encryptedData, String sessionKey, String iv) {
        log.info("============小程序登录解析数据方法开始==========");
        String result = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");
        JSONObject userInfo = new JSONObject();
        if (null != result && result.length() > 0) {
            userInfo = JSONObject.parseObject(result);
        }
        log.info("result: " + userInfo);
        log.info("============小程序登录解析数据方法结束==========");
        return userInfo;
    }

    /**
     * 微信公众号(网页)授权登录
     *
     * @param code
     * @return
     */
    public static Map<String, Object> webSiteLogin(String code) {
        log.info("============微信公众号(网页)授权开始===========");
        WxProperties properties = WeiXinPropertiesUtils.getWxProperties();

        log.info("============微信公众号(网页)授权结束===========");
        return null;
    }

}
