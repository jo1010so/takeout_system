package com.jojo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "amap")
@Data
public class AmapProperties {
    private String key;
}
