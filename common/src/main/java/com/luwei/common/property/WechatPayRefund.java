package com.luwei.common.property;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信退款请求包
 */
@Data
public class WechatPayRefund {
    @ApiModelProperty("appid")
    private String appId;

    @ApiModelProperty("mch_id")
    private String mchId;

    //随机字符串
    @ApiModelProperty("nonce_str")
    private String nonceStr;

    //签名
    @ApiModelProperty("sign")
    private String sign;

    @ApiModelProperty("订单编号--商家的")
    private String outTradeNo;

    @ApiModelProperty("退款编号--通常和订单编号相同")
    private String outRefundNo;

    @ApiModelProperty("订单金额")
    private Integer totalFee;

    @ApiModelProperty("退款金额")
    private Integer refundFee;

}
