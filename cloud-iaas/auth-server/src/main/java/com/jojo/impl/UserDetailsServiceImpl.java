package com.jojo.impl;

import com.jojo.constant.AuthConstatans;
import com.jojo.factory.LoginStrategyFactory;
import com.jojo.strategy.impl.SysUserLoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目自己的认证流程
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginStrategyFactory loginStrategyFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        //从请求头中获取登录类型
        String loginType = request.getHeader(AuthConstatans.LOGIN_TYPE);

        if (!StringUtils.hasText(loginType)) {
            throw new InternalAuthenticationServiceException("非法登录，登录类型不匹配");
        }
        //根据请求类型获取实例，进行认证
        return  loginStrategyFactory.getInstance(loginType).realLogin(username);
    }
}
