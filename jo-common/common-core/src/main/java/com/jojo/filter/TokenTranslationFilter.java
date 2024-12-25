package com.jojo.filter;

import com.alibaba.fastjson.JSONObject;
import com.jojo.constant.AuthConstatans;
import com.jojo.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * token 过滤器
 */
@Component
public class TokenTranslationFilter extends OncePerRequestFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * token转换过滤器
     *  前提：只负责处理携带token的请求，然后将认证的用户信息转换出来
     *       没有携带token的请求已交给security资源处理器类的处理器进行处理
     *
     *  1.获取token
     *  2.判断token是否有值
     *      有： token转换为用户信息，
     *          将用户信息转换为security框架认识的用户信息对象，
     *          再将认识的用户信息对象存放到当前资源服务器的容器中
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationValue = request.getHeader(AuthConstatans.AUTHORIZATION);
        if (StringUtils.hasText(authorizationValue)) {
            String token = authorizationValue.replaceFirst(AuthConstatans.BEARER, "");
            if (StringUtils.hasText(token)) {
                //每次请求token都需要续签
                //从redis中获取token的存活时长
                Long expire = stringRedisTemplate.getExpire(AuthConstatans.LOGIN_TOKEN_PREFIX + token);
                //判断是否超过系统指定的阈值
                if(expire < AuthConstatans.TOKEN_EXPIRE_THRESHOLD_TIME){
                    //给当前用户的token续签（本质增加token在redis中的存活时长）
                    stringRedisTemplate.expire(AuthConstatans.LOGIN_TOKEN_PREFIX+token,AuthConstatans.TOKEN_TIME, TimeUnit.SECONDS);
                }
                String userJsonStr = stringRedisTemplate.opsForValue().get(AuthConstatans.LOGIN_TOKEN_PREFIX+token);
                //将json格式字符串的认证用户信息转换为认证用户对象
                SecurityUser securityUser = JSONObject.parseObject(userJsonStr, SecurityUser.class);
                //处理权限
                assert securityUser != null;
                Set<SimpleGrantedAuthority> collect = securityUser.getPerms().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
                //创建UsernamePasswordAuthenticationToken对象
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser,null,collect);
                //将认证用户对象存放到当前模块的上下文中
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        //注意过滤器执行完之后，得往后传
        filterChain.doFilter(request,response);
    }
}
