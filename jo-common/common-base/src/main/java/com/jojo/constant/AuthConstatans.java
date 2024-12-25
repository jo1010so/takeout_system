package com.jojo.constant;

/**
 * 认证授权常量类
 */
public interface AuthConstatans {

    /**
     * 在请求头中存放token值的key
     */
    String AUTHORIZATION = "Authorization";
    /**
     * token值的前缀
     */
    String BEARER = "bearer ";
    /**
     * token作为key一部分存放在redis中的前缀
     */
    String LOGIN_TOKEN_PREFIX = "login_token:";
    /**
     * 登录url
     */
    String LOGIN_URL = "/doLogin";
    /**
     * 登出url
     */
    String LOGOUT_URL = "/doLogout";
    /**
     * 登录类型
     */
    String LOGIN_TYPE = "loginType";
    /**
     * 登录类型值： 商城后台管理系统用户
     */
    String SYS_USER_LOGIN = "sysUserLogin";
    /**
     * 登录类型值： 商家系统用户
     */
    String MERCHANT_LOGIN = "merchantLogin";
    /**
     * 登录类型值： 顾客系统用户
     */
    String CUSTOMER_LOGIN = "customerLogin";
    /**
     * token有效时长 4小时
     */
    Long TOKEN_TIME = 14400L;
    /**
     * TOKEN的阈值 1小时
     */
    Long TOKEN_EXPIRE_THRESHOLD_TIME = 3600L;
    //永久token
    String PERMANENT_TOKEN = "bf056798-3b74-4a95-b7a6-3169ec63fb43";
}
