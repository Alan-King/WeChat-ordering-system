package com.alan.sell.controller;

import com.alan.sell.utils.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Arrays;

@Controller
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code) {

        log.info("进入 auth方法... ");
        log.info("code ={}", code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx43ea4dc787f511f3&secret" +
                "=4f242a90491a46b493e98d706a3d04e2&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String str = restTemplate.getForObject(url, String.class);
        log.info("str = {}", str);

    }

    @GetMapping("/weixin")
    public String weixin(HttpServletRequest req) {
        log.info("进入 weixinfangfa... ");

        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        if(CheckUtil.checkSignature("weixin",signature, timestamp, nonce)){
            return echostr;
        }
        return "weixin";
    }

}

