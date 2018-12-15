package com.luwei.common.property;

import lombok.Data;

@Data
public class WechatPayApply {

    private String appId;               //公众账号ID
    private String mchId;               //商户号
    private String nonceStr;            //随机字符串
    private String sign;                //签名
    private String spbillCreateIp;      //终端IP
    private String outTradeNo;          //商户订单号
    private String totalFee;            //金额
    private String notifyUrl;           //这里notify_url是支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等
    private String body;                //商品描述根据情况修改
    private String openId;              //微信用户对一个公众号唯一
    private String tradeType;           //交易类型
    private String attch;               //附加数据原样返回
}
