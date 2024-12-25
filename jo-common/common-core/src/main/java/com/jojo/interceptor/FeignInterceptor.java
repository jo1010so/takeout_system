package com.jojo.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.jojo.constant.AuthConstatans;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * feign拦截器
 *  作用：解决服务之间调用没有token的情况
 *  浏览器 -> A服务 -> B服务
 *  定时器(类似不通过浏览器) -> A服务
 */
@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //获取当前请求的上下文对象
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        //判断是否有值
        if (ObjectUtil.isNotNull(request)){
            //获取当前请求中的token值，传递到下一个请求对象的请求头中
            String authorization = request.getHeader(AuthConstatans.AUTHORIZATION);
            requestTemplate.header(AuthConstatans.AUTHORIZATION,authorization);
            return;
        }
        requestTemplate.header(AuthConstatans.AUTHORIZATION,AuthConstatans.BEARER+AuthConstatans.PERMANENT_TOKEN);
    }
}