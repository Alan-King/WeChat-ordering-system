package com.alan.sell.handle;

import com.alan.sell.config.ProjectUrlConfig;
import com.alan.sell.enums.ResultEnum;
import com.alan.sell.exception.SellException;
import com.alan.sell.exception.SellerVerifyException;
import com.alan.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 统一异常处理
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常
    @ExceptionHandler(value = SellerVerifyException.class)
    @ResponseBody
    public ModelAndView handle(SellerVerifyException e) {
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/user/userInfo")
                .concat("?returnUrl=")
                .concat(e.getReturnUrl())
        );
    }

}
