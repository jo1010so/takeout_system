package com.jojo.util;

import com.jojo.model.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

/**
 * 认证授权工具类
 */
public class AuthUtils {
    /**
     * 获取Security容器中的认证用户对象
     * @return
     */
    public static SecurityUser getLoginUser(){
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取Security容器中的认证用户对象的用户标识
     * @return
     */
    public static Long getLoginUserId(){
        return getLoginUser().getUserId();
    }

    /**
     * 获取Security容器中认证对象的操作权限集合
     */
    public static Set<String> getLoginUserPerms(){
        return getLoginUser().getPerms();
    }
}
