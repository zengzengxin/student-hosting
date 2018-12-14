package com.luwei.model.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(
        prefix = "luwei.module.wx"
)
@Component
@Data
public class WxProperties {
    private String appId;
    private String appSecret;
    private String serverIp;
}
