package com.alan.sell.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "projecturl")
public class ProjectUrlConfig {
    public String Sell ;
}
