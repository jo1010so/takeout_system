package com.jojo.strategy;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 登录策略接口
 */
public interface LoginStrategy {
    /**
     * 真正处理登录的方法
     * @param username
     * @return
     */
    UserDetails realLogin(String username);
}
