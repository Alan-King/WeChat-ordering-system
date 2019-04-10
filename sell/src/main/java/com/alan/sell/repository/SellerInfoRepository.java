package com.alan.sell.repository;

import com.alan.sell.dataobject.ProductInfo;
import com.alan.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openid);

    SellerInfo findByUsername(String username);

}
