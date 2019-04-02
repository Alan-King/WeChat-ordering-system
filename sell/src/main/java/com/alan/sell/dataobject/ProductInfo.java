package com.alan.sell.dataobject;

import com.alan.sell.enums.ProductStatusEnum;
import com.alan.sell.utils.EnumUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 *
 Create Table

 CREATE TABLE `product_info` (
 `product_id` varchar(32) NOT NULL,
 `product_name` varchar(64) NOT NULL COMMENT '商品名称',
 `product_price` decimal(8,2) NOT NULL COMMENT '单价',
 `product_stock` int(11) NOT NULL COMMENT '库存',
 `product_description` varchar(64) DEFAULT NULL COMMENT '描述',
 `product_icon` varchar(512) DEFAULT NULL COMMENT '商品小图',
 `product_status` tinyint(3) default '0' comment '商品状态，0正常1下架',
 `category_type` int(11) NOT NULL COMMENT '类目编号',
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 PRIMARY KEY (`product_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表'

 */
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class ProductInfo {
    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;
    // `product_stock` int(11) NOT NULL COMMENT '库存',
    private Integer productStock;
    // `product_description` varchar(64) DEFAULT NULL COMMENT '描述',
    private String productDescription;
    //`product_icon` varchar(512) DEFAULT NULL COMMENT '商品小图',
    private String productIcon;
    // `product_status` tinyint(3) default '0' comment '商品状态，0正常1下架',
    private Integer productStatus = 0;
    //`category_type` int(11) NOT NULL COMMENT '类目编号',
    private Integer categoryType;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtils.getByCode(productStatus, ProductStatusEnum.class);
    }

}
