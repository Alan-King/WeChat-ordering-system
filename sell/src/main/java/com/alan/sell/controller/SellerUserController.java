package com.alan.sell.controller;

import com.alan.sell.config.ProjectUrlConfig;
import com.alan.sell.constant.CookieConstant;
import com.alan.sell.constant.RedisConstant;
import com.alan.sell.dataobject.SellerInfo;
import com.alan.sell.enums.ResultEnum;
import com.alan.sell.form.ProductForm;
import com.alan.sell.form.UserForm;
import com.alan.sell.service.SellerInfoService;
import com.alan.sell.utils.CookieUtils;
import com.alan.sell.utils.JsonUtils;
import com.sun.javafx.binding.StringFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Controller
@Slf4j
@RequestMapping("/seller/user")
public class SellerUserController {

    @Autowired
    private SellerInfoService sellerInfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @RequestMapping("/userInfo")
    public ModelAndView userInfo(@RequestParam(value = "returnUrl", required = false) String returnUrl,
                                 Map<String, Object> map) {
        if (!StringUtils.isEmpty(returnUrl)) {
            map.put("returnUrl", returnUrl);
        }
        return new ModelAndView("user/index", map);
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid UserForm userForm, BindingResult bindingResult,
                              HttpServletResponse response,
                              Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            throw new RuntimeException("用户传入数据不匹配");
        }
        //1.将用户与数据库进行匹配
        SellerInfo info = sellerInfoService.findSellerInfoByUsername(userForm.getUsername());
        if (info == null || !info.getPassword().equals(userForm.getPassword())) {
            map.put("msg", ResultEnum.LOGIN_FAILED.getMessage());
            map.put("url", userForm.getReturnUrl() == null ? "/sell/seller/order/list" : userForm.getReturnUrl());
            return new ModelAndView("common/error", map);
        }
        info.setPassword(null);
        //2.写入session
        String token = UUID.randomUUID().toString();
        String key = StringFormatter.format(RedisConstant.TOKEN_PREFIX, token).getValue();
        String value = JsonUtils.toJSON(info);
        redisTemplate.opsForValue().set(key, value,
                RedisConstant.TOKEN_EXPIRE,
                TimeUnit.SECONDS);

        //3.写入cookie
        CookieUtils.set(response, CookieConstant.TOKEN, key, CookieConstant.TOKEN_MAX_AGE);
        String url = userForm.getReturnUrl() == null ? projectUrlConfig.getSell() + "/sell/seller/order/list" :
                userForm.getReturnUrl();
        return new ModelAndView("redirect:" + url);
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {


        Cookie tokenCookie = CookieUtils.get(request, CookieConstant.TOKEN);

        if (tokenCookie != null) {
            CookieUtils.set(response, tokenCookie.getName(), null, 0);
            redisTemplate.delete(tokenCookie.getValue());
        }
        map.put("msg", ResultEnum.LOGOUT_SUCECED.getMessage());
        map.put("url", "/sell/seller/user/userInfo");
        return new ModelAndView("common/success", map);
    }
}
