package com.alan.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountCongig {
    private String mAppid;
    private String mAppSecret;
    private String mchId;
    private String mchKey;
    private String keyPath;
    private String notifyUrl;

}
