package com.alan.sell.dataobject;

import com.alan.sell.enums.OrderStatusEnum;
import com.alan.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    //`order_id` varchar(32) NOT NULL,
    @Id
    private String orderId;
    //  `buyer_name` varchar(32) NOT NULL COMMENT '买家名字',
    private String buyerName;
    //          `buyer_phone` varchar(32) NOT NULL COMMENT '买家电话',
    private String buyerPhone;
    //            `buyer_address` varchar(128) NOT NULL COMMENT '买家地址',
    private String buyerAddress;
    //            `buyer_openid` varchar(64) NOT NULL COMMENT '买家微信openid',
    private String buyerOpenid;
    //            `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
    private BigDecimal orderAmount;
    //            `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态，默认0新下单',
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    //            `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态，默认0未支付',
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    //            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    private Date createTime;
    //            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    private Date updateTime;

}
