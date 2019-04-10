package com.alan.sell.aspect;

import com.alan.sell.constant.CookieConstant;
import com.alan.sell.constant.RedisConstant;
import com.alan.sell.exception.SellerVerifyException;
import com.alan.sell.utils.CookieUtils;
import com.sun.javafx.binding.StringFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.cookie.CookieAttributeHandler;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class SellerVerifyAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut(value = "execution(public * com.alan.sell.controller.Seller*.*(..))"
            + " && !execution(public * com.alan.sell.controller.SellerUserController.*(..) )")
    public void pointCut(){}

    @Before(value = "pointCut()")
    public void doVerify() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = servletRequestAttributes.getRequest();

        //查询Cookie
        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN);

        if (cookie == null ){
            log.warn("【登录验证】 cookie中无用户token信息");
            throw new SellerVerifyException(request.getRequestURL().toString());
        }
        String redisValue = redisTemplate.opsForValue().get(cookie.getValue());
        if (StringUtils.isEmpty(redisValue)) {
            log.warn("【登录验证】 redis中无用户token信息");
            throw new SellerVerifyException(request.getRequestURL().toString());
        }

    }



}
