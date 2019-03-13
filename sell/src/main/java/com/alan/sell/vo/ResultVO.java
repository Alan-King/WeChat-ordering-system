package com.alan.sell.vo;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import org.springframework.boot.jackson.JsonComponent;

@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;

    public ResultVO successWithNull() {
        return successWithData(null);
    }

    public ResultVO<T> successWithData(T data) {
        setCode(0);
        setMsg("成功");
        setData(data);
        return this;
    }

    public static ResultVO success(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(data);

        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
