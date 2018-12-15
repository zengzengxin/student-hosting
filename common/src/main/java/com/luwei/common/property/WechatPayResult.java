package com.luwei.common.property;

import lombok.Data;

/**
 * 微信支付结果
 */
@Data
public class WechatPayResult {

    private String appid;
    private String bankType;
    private String cashFee;
    private String feeType;
    private String isSubscribe;
    private String mchId;
    private String nonceStr;
    private String openid;
    private String outTradeNo;
    private String resultCode;
    private String returnCode;
    private String sign;
    private String timeEnd;
    private String totalFee;
    private String tradeType;
    private String transactionId;
}
