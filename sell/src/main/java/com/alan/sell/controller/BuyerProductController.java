package com.alan.sell.controller;

import com.alan.sell.dataobject.ProductCategory;
import com.alan.sell.dataobject.ProductInfo;
import com.alan.sell.service.CategoryService;
import com.alan.sell.service.ProductService;
import com.alan.sell.vo.CategoryVO;
import com.alan.sell.vo.ProductVO;
import com.alan.sell.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    public ResultVO list() {
        //查处所有在架商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        //查询类目
        List<ProductCategory> categoryList = categoryService.findAll();
        List<CategoryVO> categoryVOList = new ArrayList<>(categoryList.size());
        for (ProductCategory productCategory : categoryList) {
            CategoryVO categoryVO = new CategoryVO();
            categoryVO.setCategoryName(productCategory.getCategoryName());
            categoryVO.setCategoryType(productCategory.getCategoryType());
            List<ProductVO> foods = new ArrayList<>();
            categoryVO.setProductVOList(foods);
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(categoryVO.getCategoryType())) {
                    ProductVO productVO = new ProductVO();
                    productVO.setProductId(productInfo.getProductId());
                    productVO.setProductName(productInfo.getProductName());
                    productVO.setProductPrice(productInfo.getProductPrice());
                    productVO.setProductDescription(productInfo.getProductDescription());
                    productVO.setProductIcon(productInfo.getProductIcon());
                    //上面的代码使用BeanUtils方法代替
                   // BeanUtils.copyProperties(productInfo, productVO);
                    foods.add(productVO);
                }
            }
            categoryVOList.add(categoryVO);
        }

        return ResultVO.success(categoryVOList);
    }
}
