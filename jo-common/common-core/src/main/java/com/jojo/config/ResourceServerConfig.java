package com.jojo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojo.constant.HttpConstants;
import com.jojo.constant.ResourceConstants;
import com.jojo.constant.StatusEnum;
import com.jojo.filter.TokenTranslationFilter;
import com.jojo.model.Result;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.io.PrintWriter;

/**
 * SpringSecurity框架的资源服务配置类
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Resource
    @Qualifier("tokenTranslationFilter")
    private TokenTranslationFilter tokenTranslationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域请求
        http.cors().disable();
        //关闭跨站请求伪造
        http.csrf().disable();
        //关闭session使用策略
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //编写一个token解析过滤器，将token转换为security安全框架能够认证的用户信息，再存放到当前资源服务的容器中
        //在认证身份过滤器之前，给到已认证过的身份信息
        http.addFilterBefore(tokenTranslationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/customer-service/register/**").permitAll();

        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())  // 处理没有携带token的请求
                .accessDeniedHandler(accessDeniedHandler());      // 处理携带token但是权限不足的请求

        //配置其他请求
        http.authorizeHttpRequests()
                .antMatchers(ResourceConstants.RESOURCE_ALLOW_URLS)
                .permitAll()
                .antMatchers("/customer-service/register/**")
                .permitAll(); //除了放行的请求都需要进行身份认证
    }
//.anyRequest().authenticated()
    /**
     * 处理：请求没有带token
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (request, response, authException) -> {
            response.setContentType(HttpConstants.APPLICATION_JSON);
            response.setCharacterEncoding(HttpConstants.UTF_8);

            Result<Object> result = Result.fail(StatusEnum.UN_AUTHORIZATION);
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(result));
            writer.flush();
            writer.close();
        };
    }

    /**
     * 处理：请求中携带token但是权限不足的情况
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return (request, response, accessDeniedException) -> {
            response.setContentType(HttpConstants.APPLICATION_JSON);
            response.setCharacterEncoding(HttpConstants.UTF_8);

            Result<Object> result = Result.fail(StatusEnum.ACCESS_DENY_FAIL);
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(result));
            writer.flush();
            writer.close();
        };
    }

}
