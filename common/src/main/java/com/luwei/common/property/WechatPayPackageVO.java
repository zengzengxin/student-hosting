package com.luwei.common.property;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Author: huanglp
 * Date: 2018-12-29
 */
@Data
@Accessors(chain = true)
public class WechatPayPackageVO {

    private String appId;

    private String packages;

    private Long timestamp;

    private String nonceStr;

    private String signType;

    private String paySign;

    @ApiModelProperty("是否需要支付" )
    private boolean isNeedPay;

}
