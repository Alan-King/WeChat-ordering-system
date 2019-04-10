package com.alan.sell.service.Impl;

import com.alan.sell.dataobject.SellerInfo;
import com.alan.sell.repository.SellerInfoRepository;
import com.alan.sell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;
    @Override
    public SellerInfo findSellerInfoByUsername(String username) {
        return sellerInfoRepository.findByUsername(username);
    }

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }
}
