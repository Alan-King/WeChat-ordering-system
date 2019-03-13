package com.alan.sell.converter;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.alan.sell.dto.OrderDTO;

public class OrderDTO2PayRequestConverter {

    public static PayRequest convert(OrderDTO orderDTO){
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName("微信订餐支付");
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        return payRequest;
    }
}
