package com.alan.sell.form;

import lombok.Data;

import javax.persistence.Id;

@Data
public class UserForm {

    private String sellerId;

    private String username;

    private String password;

    private String openid;

    private String returnUrl;

}
