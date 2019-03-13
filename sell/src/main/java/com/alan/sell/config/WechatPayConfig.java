package com.alan.sell.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatPayConfig {

    @Autowired
    private WechatAccountCongig wechatAccountCongig;

    @Bean
    public BestPayServiceImpl bestPayService(){
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }

    private WxPayH5Config wxPayH5Config(){
        WxPayH5Config wxPayH5Config = new WxPayH5Config();

        wxPayH5Config.setAppId(wechatAccountCongig.getMAppid());
        wxPayH5Config.setAppSecret(wechatAccountCongig.getMAppSecret());
        wxPayH5Config.setMchId(wechatAccountCongig.getMchId());
        wxPayH5Config.setMchKey(wechatAccountCongig.getMchKey());
        wxPayH5Config.setKeyPath(wechatAccountCongig.getKeyPath());
        wxPayH5Config.setNotifyUrl(wechatAccountCongig.getNotifyUrl());

        return wxPayH5Config;
    }


}
