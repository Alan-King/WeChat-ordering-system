package com.alan.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW(0, "新下单"),
    FINISHED(1, "完成"),
    ACCEPTED(2, "接受"),
    CANCLED(3, "取消");

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
