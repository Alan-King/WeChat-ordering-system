package com.alan.sell.repository;

import com.alan.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
