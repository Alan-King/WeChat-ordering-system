package com.alan.sell.exception;

import lombok.Getter;

@Getter
public class SellerVerifyException extends RuntimeException {
    private String returnUrl;

    public SellerVerifyException(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
