package com.luwei.common.util;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: ffq
 * @Description:
 * @Date: Create in 17:06 2018/5/2
 */
public class WeiXinQRCode {
    @ApiModelProperty("获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。" )
    private String ticket;
    @ApiModelProperty("该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。" )
    private Integer expire_seconds;
    @ApiModelProperty("二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片" )
    private String url;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpire_seconds() {
        return expire_seconds;
    }

    public void setExpire_seconds(Integer expire_seconds) {
        this.expire_seconds = expire_seconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
