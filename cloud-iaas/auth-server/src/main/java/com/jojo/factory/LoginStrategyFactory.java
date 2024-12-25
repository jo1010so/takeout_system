package com.jojo.factory;

import com.jojo.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录策略工厂类
 */
@Component
public class LoginStrategyFactory {

    @Autowired
    private Map<String,LoginStrategy>  loginStrategyMap = new HashMap<>();

    /**
     * 根据用户登录类型获取具体的登录策略
     * @param loginType
     * @return
     */
    public LoginStrategy getInstance(String loginType){
        if (loginStrategyMap.containsKey(loginType))
            return loginStrategyMap.get(loginType);
        else
            throw new RuntimeException("非法登录，登录类型不存在");
    }

}
