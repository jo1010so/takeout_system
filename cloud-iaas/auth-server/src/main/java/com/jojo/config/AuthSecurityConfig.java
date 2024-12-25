package com.jojo.config;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojo.constant.AuthConstatans;
import com.jojo.constant.HttpConstants;
import com.jojo.constant.StatusEnum;
import com.jojo.impl.UserDetailsServiceImpl;
import com.jojo.model.LoginResult;
import com.jojo.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.PrintWriter;
import java.time.Duration;
import java.util.UUID;

//WebSecurityConfigurerAdapter

@Configuration
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置Security安全框架走自己的认证流程
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //函数参数为实现userDetails接口的实现类
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域请求
        http.cors().disable();
        //关闭跨站请求伪造
        http.csrf().disable();
        //关闭session使用策略
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/customer-service/register/**").permitAll();

        //配置登录信息
        http.formLogin()
                .loginProcessingUrl(AuthConstatans.LOGIN_URL) // 设置登录URL
                .successHandler(authenticationSuccessHandler())   // 设置登录成功处理器
                .failureHandler(authenticationFailureHandler());  // 设置登录失败处理器
        //配置登出信息
        http.logout()
                .logoutUrl(AuthConstatans.LOGOUT_URL)  //设置登出URL
                .logoutSuccessHandler(logoutSuccessHandler()); // 设置登出成功处理器


    }

    /**
     * 登录成功处理器
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return (request, response, authentication) -> {
            //设置响应头信息
            response.setHeader(HttpConstants.CONTENT_TYPE,HttpConstants.APPLICATION_JSON);
            response.setCharacterEncoding(HttpConstants.UTF_8);
            //使用UUID当作token
            String token = UUID.randomUUID().toString();
            //从security框架中获取认证用户对象(SecurityUser)并转换为json格式的字符串
            String userJsonStr = JSONObject.toJSONString(authentication.getPrincipal());
            //将token当作key，认证用户对象的json格式的字符串当前value存放到redis中
            stringRedisTemplate.opsForValue().set(AuthConstatans.LOGIN_TOKEN_PREFIX+token,userJsonStr,
                    Duration.ofSeconds(AuthConstatans.TOKEN_TIME));
            //创建一个响应结果的对象
            Result<Object> result =Result.success(new LoginResult(token,AuthConstatans.TOKEN_TIME));
            //返回结果
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    /**
     * 登录失败处理器
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (request, response, exception) -> {
            //设置响应头信息
            response.setHeader(HttpConstants.CONTENT_TYPE,HttpConstants.APPLICATION_JSON);
            response.setCharacterEncoding(HttpConstants.UTF_8);
            //创建统一响应结果对象
            Result<Object> result = new Result<>();
            result.setCode(StatusEnum.OPERATION_FAIL.getCode());
            if (exception instanceof BadCredentialsException) {
                result.setMsg("用户名或密码有误");
            } else if (exception instanceof UsernameNotFoundException) {
                result.setMsg("用户不存在");
            } else if (exception instanceof AccountExpiredException) {
                result.setMsg("帐号异常，请联系管理员");
            } else if (exception instanceof AccountStatusException) {
                result.setMsg("帐号异常，请联系管理员");
            } else if (exception instanceof InternalAuthenticationServiceException) {
                result.setMsg(exception.getMessage());
            } else {
                result.setMsg(exception.getMessage());
            }
            // 返回结果
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    /**
     * 登出成功处理器
     * @return
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return (request, response, authentication) -> {
            //设置响应头信息
            response.setHeader(HttpConstants.CONTENT_TYPE,HttpConstants.APPLICATION_JSON);
            response.setCharacterEncoding(HttpConstants.UTF_8);

            //从请求头中获取token
            String authorization = request.getHeader(AuthConstatans.AUTHORIZATION);
            String token = authorization.replace(AuthConstatans.BEARER, "");
            //将当前token从redis中删除
            stringRedisTemplate.delete( AuthConstatans.LOGIN_TOKEN_PREFIX+token);
            Result<Object> result = Result.success();
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(result));
            writer.flush();
            writer.close();
        };
    }
    @Bean
    public PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }

}
