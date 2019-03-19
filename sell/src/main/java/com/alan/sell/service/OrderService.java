package com.alan.sell.service;

import com.alan.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

public interface OrderService {

    OrderDTO create(OrderDTO orderDTO);

    OrderDTO findOne(String orderId);

    //查询某人的订单列表
    Page<OrderDTO> findList(String buyerOpenId, Pageable pageAble);

    //查询所有人的订单列表
    Page<OrderDTO> findList(Pageable pageAble);

    OrderDTO cancel(OrderDTO orderDTO);

    OrderDTO finish(OrderDTO orderDTO);

    OrderDTO paid(OrderDTO orderDTO);

}
