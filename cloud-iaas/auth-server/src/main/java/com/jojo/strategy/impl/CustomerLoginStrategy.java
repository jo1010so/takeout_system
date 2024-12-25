package com.jojo.strategy.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jojo.constant.AuthConstatans;
import com.jojo.domain.LoginCustomer;
import com.jojo.domain.LoginSysUser;
import com.jojo.mapper.LoginCustomerMapper;
import com.jojo.model.SecurityUser;
import com.jojo.strategy.LoginStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(AuthConstatans.CUSTOMER_LOGIN)
public class CustomerLoginStrategy implements LoginStrategy {

    @Resource
    private LoginCustomerMapper loginCustomerMapper;

    @Override
    public UserDetails realLogin(String username) {
        //根据用户名称查询顾客对象
        LoginCustomer loginCustomer = loginCustomerMapper.selectOne(new LambdaQueryWrapper<LoginCustomer>().eq(LoginCustomer::getUsername, username));

        if (ObjectUtil.isNotNull(loginCustomer)){
            //创建安全用户对象SecurityUser
            SecurityUser securityUser = new SecurityUser();
            securityUser.setCustomerId(loginCustomer.getId());
            securityUser.setPassword(loginCustomer.getPassword());
            securityUser.setLoginType(AuthConstatans.CUSTOMER_LOGIN);
            securityUser.setAuthorize(loginCustomer.getAuthorize());
            securityUser.setStatus(loginCustomer.getStatus());
            return securityUser;
        }
        return null;
    }
}
