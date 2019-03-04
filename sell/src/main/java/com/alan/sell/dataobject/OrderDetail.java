package com.alan.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
public class OrderDetail {
    //`detail_id` varchar(32) NOT NULL,
    @Id
    private String detailId;
    //`order_id` varchar(32) NOT NULL,
    private String orderId;
    //`product_id` varchar(32) NOT NULL COMMENT '商品id',
    private String productId;
    //`product_name` varchar(64) NOT NULL COMMENT '商品名字',
    private String productName;
    //`product_price` decimal(8,2) NOT NULL COMMENT '商品价格',
    private BigDecimal productPrice;
    //`product_quantity` int(11) NOT NULL COMMENT '商品数量',
    private Integer productQuantity;
    //`product_icon` varchar(512) DEFAULT NULL COMMENT '商品小图',
    private String productIcon;
    //`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    private Date createTime;
    //`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    private Date updateTime;

}
