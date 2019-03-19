package com.alan.sell.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum implements EnumCode{
    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功"),
    REFUNDED(3,"已退款")
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
