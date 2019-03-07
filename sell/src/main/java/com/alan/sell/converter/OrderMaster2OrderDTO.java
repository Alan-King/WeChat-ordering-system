package com.alan.sell.converter;

import com.alan.sell.dataobject.OrderMaster;
import com.alan.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTO {

    public static OrderDTO converter(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> converterList(List<OrderMaster> orderMasterList){
        List<OrderDTO> orderDTOList=orderMasterList.stream()
                .map(e -> converter(e))
                .collect(Collectors.toList());
        return orderDTOList;
    }
}
