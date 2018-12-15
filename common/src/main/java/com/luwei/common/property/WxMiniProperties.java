package com.luwei.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "luwei.module.wx-mini")
@Component
@Data
public class WxMiniProperties {
    private String appId;
    private String appSecret;
}
