package com.jojo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class FlowUtils {

    @Value("${spring.rabbitmq.blockTime}")
    private int blockTime;

    @Resource
    private StringRedisTemplate template;

    public boolean limitOnceCheck(String key){
        if(Boolean.TRUE.equals(template.hasKey(key))){
            return false;
        }else {
            //进入冷却时间
            template.opsForValue().set(key,"",blockTime, TimeUnit.SECONDS);
        }
        return true;
    }
}
