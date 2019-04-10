package com.alan.sell.service;

import com.alan.sell.dataobject.SellerInfo;

public interface SellerInfoService {

    SellerInfo findSellerInfoByUsername(String username);

    SellerInfo findSellerInfoByOpenid(String openid);

}
