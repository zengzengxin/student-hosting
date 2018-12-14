package com.luwei.model.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShareParam {

    @ApiModelProperty(value = "流水")
    private String nonceStr;
    @ApiModelProperty(value = "时间撮")
    private String timestamp;
    @ApiModelProperty(value = "签名url")
    private String signature;
    @ApiModelProperty(value = "微信appId")
    private String appId;
    @ApiModelProperty(value = "jsapi_ticket")
    private String jsapi_ticket;
}
