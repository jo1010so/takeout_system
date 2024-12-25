package com.jojo.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojo.config.WhiteUrlsConfig;
import com.jojo.constant.AuthConstatans;
import com.jojo.constant.HttpConstants;
import com.jojo.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;


/**
 * 全局token过滤器
 *  前后端约定好token存放的位置，请求头的Authorization bearer token
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private WhiteUrlsConfig whiteUrlsConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 校验token
     * 1.获取请求路径
     * 2.判断请求路径是否可以放行
     *   放行：不需要验证身份
     *   不放行：需要对身份进行认证
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求对象
        ServerHttpRequest request = exchange.getRequest();
        //获取请求路径
        String path = request.getPath().toString();
        //判断当前请求路径是否放行，查看是否在白名单中
        if (whiteUrlsConfig.getAllowUrls().contains(path)){
            //包含白名单，则放行
            return chain.filter(exchange);
        }
        //请求路径不包含在白名单中，不要进行身份验证
        //从约定好的位置获取Authorization的值，值的格式为:bearer token
        String authorizationValue = request.getHeaders().getFirst(AuthConstatans.AUTHORIZATION);
        //判断值是否存在
        if (StringUtils.hasText(authorizationValue)) {
            //从authorizationValue中获取token
            //将前缀’bearer ‘去掉
            String token = authorizationValue.replace(AuthConstatans.BEARER, "");
            //判断token是否有值 并且 该值在redis中存在
            if (StringUtils.hasText(token) && Boolean.TRUE.equals(stringRedisTemplate.hasKey(AuthConstatans.LOGIN_TOKEN_PREFIX+token))){
                //身份验证通过，放行
                return chain.filter(exchange);
            }
        }

        //流程走到这里说明验证身份没有通过或请求不合法
        log.error("拦截非法请求，时间{}，请求API路径{}",new Date(),path);

        //获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        //设置响应头信息
        response.getHeaders().set(HttpConstants.CONTENT_TYPE,HttpConstants.APPLICATION_JSON);
        //设置响应消息
        Result<Object> result = Result.unauthorized();

        //创建一个ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return -5;
    }
}
