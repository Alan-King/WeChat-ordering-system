package com.alan.sell.service;

import com.alan.sell.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {

    ProductCategory findOneById(Integer id);

    //查询所有
    List<ProductCategory> findAll();

    //根据类型查询
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    //新增和更新
    ProductCategory save(ProductCategory productCategory);

}
