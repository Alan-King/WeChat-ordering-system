package com.alan.sell.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductForm {
    private String productId;

    private String productName;

    private BigDecimal productPrice;
    // `product_stock` int(11) NOT NULL COMMENT '库存',
    private Integer productStock;
    // `product_description` varchar(64) DEFAULT NULL COMMENT '描述',
    private String productDescription;
    //`product_icon` varchar(512) DEFAULT NULL COMMENT '商品小图',
    private String productIcon;
    //`category_type` int(11) NOT NULL COMMENT '类目编号',
    private Integer categoryType;

}
