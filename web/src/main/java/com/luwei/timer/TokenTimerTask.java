package com.luwei.timer;

import com.luwei.common.util.WeChatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: huanglp
 * Date: 2019-01-10
 */
@Component
@Slf4j
public class TokenTimerTask {

    @Value("${luwei.module.wx.appId}")
    private String appId;

    @Value("${luwei.module.wx.appSecret}")
    private String appSecret;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void getTokenAndTicket() {
        log.info("=============调用微信接口刷新access_token和ticket的定时任务启动=============");
        long start = System.currentTimeMillis();

        log.info("appId: {}", appId);
        log.info("appSecret: {}", appSecret);
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, Object> params = new HashMap<>(3);
        params.put("grant_type", "client_credential");
        params.put("appid", appId);
        params.put("secret", appSecret);
        Map<String, Object> tokenMap = WeChatUtils.getWechatReturn(params, url);
        WeChatUtils.TOKEN = (String) tokenMap.get("access_token");
        Map<String, Object> ticketMap = WeChatUtils.getTicket(WeChatUtils.TOKEN);
        WeChatUtils.TICKET = (String) ticketMap.get("ticket");
        log.info("token: {}", WeChatUtils.TOKEN);
        log.info("ticket: {}", WeChatUtils.TICKET);

        long end = System.currentTimeMillis();
        log.info("共耗时{}毫秒", String.valueOf(end - start));
        log.info("=============调用微信接口刷新access_token和ticket的定时任务结束=============");
    }

}
