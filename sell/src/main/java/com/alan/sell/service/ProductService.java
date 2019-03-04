package com.alan.sell.service;

import com.alan.sell.dataobject.ProductCategory;
import com.alan.sell.dataobject.ProductInfo;
import com.alan.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOneById(String id);

    //查询所有上架商品
    List<ProductInfo> findUpAll();
    //查询所有商品
    Page<ProductInfo> findAll(Pageable pageable);

    //新增和更新
    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

}
