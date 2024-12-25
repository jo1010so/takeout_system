package com.jojo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Security安全框架认识的安全用户对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecurityUser implements UserDetails {

    //外卖后台管理系统用户的相关属性
    private Long userId;
    private String username;
    private String password;
    private Integer status;
    private String loginType;
    private Set<String> perms = new HashSet<>();

    //顾客的相关属性
    private Integer customerId;
    private Integer authorize;


    //商家的相关属性
    private Integer merchantId;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.loginType+this.userId;
    }

    public Set<String> getPerms() {
        HashSet<String> finalPermsSet = new HashSet<>();
        //循环遍历用户权限集合
        perms.forEach(perm ->{
            //判断是否包含”,“
            if (perm.contains(",")) {
                String[] realPerms = perm.split(",");
                finalPermsSet.addAll(Arrays.asList(realPerms));
            }else {
                finalPermsSet.add(perm);
            }
        });
        return finalPermsSet;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status == 1;
    }

    @Override
    public boolean isEnabled() {
        return this.status == 1;
    }
}
