package com.luwei.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luwei.common.util.AesCbcUtil;
import com.luwei.model.wechat.WxMiniProperties;
import com.luwei.model.wechat.WxProperties;
import com.riversoft.weixin.common.oauth2.AccessToken;
import com.riversoft.weixin.common.oauth2.OpenUser;
import com.riversoft.weixin.open.base.AppSetting;
import com.riversoft.weixin.open.oauth2.OpenOAuth2s;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

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
    public static JSONObject login(String code) {
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
    public static JSONObject decryptWxData(String encryptedData, String sessionKey, String iv) throws Exception {
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
     * @param state
     * @return
     */
    public static OpenUser webSiteLogin(String code, String state) {
        log.info("============微信公众号(网页)授权开始===========");
        WxProperties properties = WeiXinPropertiesUtils.getWxProperties();
        AppSetting appSetting = new AppSetting(properties.getAppId(), properties.getAppSecret());
        OpenOAuth2s openOAuth2s = OpenOAuth2s.with(appSetting);
        AccessToken accessToken = openOAuth2s.getAccessToken(code);

        //获取用户信息
        OpenUser openUser = openOAuth2s.userInfo(accessToken.getAccessToken(), accessToken.getOpenId());
        log.info("============微信公众号(网页)授权结束===========");
        return openUser;
    }

}
