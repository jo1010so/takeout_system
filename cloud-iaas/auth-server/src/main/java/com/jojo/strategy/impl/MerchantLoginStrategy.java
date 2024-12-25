package com.jojo.strategy.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jojo.constant.AuthConstatans;
import com.jojo.domain.LoginMerchant;
import com.jojo.mapper.LoginMerchantMapper;
import com.jojo.model.SecurityUser;
import com.jojo.strategy.LoginStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(AuthConstatans.MERCHANT_LOGIN)
public class MerchantLoginStrategy implements LoginStrategy {

    @Resource
    private LoginMerchantMapper loginMerchantMapper;

    @Override
    public UserDetails realLogin(String username) {
        //根据用户名称查询顾客对象
        LoginMerchant loginMerchant = loginMerchantMapper.selectOne(new LambdaQueryWrapper<LoginMerchant>().eq(LoginMerchant::getUsername, username));
        if (ObjectUtil.isNotNull(loginMerchant)){
            //创建安全用户对象SecurityUser
            SecurityUser securityUser = new SecurityUser();
            securityUser.setCustomerId(loginMerchant.getId());
            securityUser.setPassword(loginMerchant.getPassword());
            securityUser.setLoginType(AuthConstatans.MERCHANT_LOGIN);
            securityUser.setAuthorize(loginMerchant.getAuthorize());
            securityUser.setStatus(loginMerchant.getStatus());
            return securityUser;
        }
        return null;
    }
}
