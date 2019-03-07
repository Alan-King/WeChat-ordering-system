package com.alan.sell.converter;

import com.alan.sell.dataobject.OrderDetail;
import com.alan.sell.dto.CartDTO;
import com.alan.sell.dto.OrderDTO;
import com.alan.sell.form.OrderForm;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();

        List<OrderDetail> orderDetailist = JSON.parseArray(orderForm.getItems(), OrderDetail.class);
        orderDTO.setOrderDetailList(orderDetailist);
        return orderDTO;
    }
}
