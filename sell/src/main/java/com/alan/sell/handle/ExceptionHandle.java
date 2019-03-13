package com.alan.sell.handle;

import com.alan.sell.enums.ResultEnum;
import com.alan.sell.exception.SellException;
import com.alan.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO handle(Exception e) {
        if (e instanceof SellException) {
            SellException exception = (SellException) e;
            return ResultVO.error(exception.getCode(), exception.getMessage());
        }else {
            log.error("系统异常 ：{}", e);
            return ResultVO.error(ResultEnum.SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }
}
