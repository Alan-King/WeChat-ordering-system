package com.alan.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {

    UP(0,"在架状态"),
    DOWN(1,"下架状态"),
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
