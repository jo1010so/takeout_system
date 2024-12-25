package com.jojo.strategy.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jojo.constant.AuthConstatans;
import com.jojo.domain.LoginSysUser;
import com.jojo.mapper.LoginSysUserMapper;
import com.jojo.model.SecurityUser;
import com.jojo.strategy.LoginStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商城后台管理系统登录策略的具体实现
 */
@Service(AuthConstatans.SYS_USER_LOGIN)
public class SysUserLoginStrategy implements LoginStrategy {

    @Resource
    private LoginSysUserMapper loginSysUserMapper;

    @Override
    public  UserDetails realLogin(String username) {
        //根据用户名称查询用户对象
        LoginSysUser loginSysUser = loginSysUserMapper.selectOne(new LambdaQueryWrapper<LoginSysUser>()
                .eq(LoginSysUser::getUsername, username)
        );
        if (ObjectUtil.isNotNull(loginSysUser)){
            //根据用户标识查询用户的权限集合
            /*Set<String> perms = loginSysUserMapper.selectPermsByUserId(loginSysUser.getUserId());*/
            //创建安全用户对象SecurityUser
            SecurityUser securityUser = new SecurityUser();
            securityUser.setStatus(loginSysUser.getStatus());
            securityUser.setUserId(loginSysUser.getUserId());
            securityUser.setPassword(loginSysUser.getPassword());
            securityUser.setLoginType(AuthConstatans.SYS_USER_LOGIN);
            //判断用户权限是否有值
            /*if (CollectionUtil.isNotEmpty(perms) && perms.size() != 0)
                securityUser.setPerms(perms);*/
            return securityUser;
        }

        return null;
    }
}
