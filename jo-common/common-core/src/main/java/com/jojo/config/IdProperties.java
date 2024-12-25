package com.jojo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "identify")
@Data
public class IdProperties {
    private String url ;

    private String appKey ;

    private String appSecret ;

    private String appCode ;
}
