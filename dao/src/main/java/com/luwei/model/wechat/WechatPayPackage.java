package com.luwei.model.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WechatPayPackage {

    private String appId;

    private String packages;

    private String timestamp;

    private String nonceStr;

    private String signType;

    private String paySign;

    @ApiModelProperty("是否需要支付")
    private boolean isNeedPay;

}
