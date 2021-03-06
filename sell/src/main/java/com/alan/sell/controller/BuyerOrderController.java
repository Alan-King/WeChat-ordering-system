package com.alan.sell.controller;

import com.alan.sell.converter.OrderForm2OrderDTOConverter;
import com.alan.sell.dataobject.OrderDetail;
import com.alan.sell.dto.OrderDTO;
import com.alan.sell.enums.ResultEnum;
import com.alan.sell.exception.SellException;
import com.alan.sell.form.OrderForm;
import com.alan.sell.service.OrderService;
import com.alan.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.JsonPath;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(OrderForm orderForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("订单参数不正确,orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        ResultVO<Map<String, String>> resultVO = new ResultVO<>();

        String orderId = orderService.create(orderDTO).getOrderId();
        Map<String, String> map = new HashMap<>();

        map.put("orderId", orderId);

        return resultVO.successWithData(map);
    }

    @RequestMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam(value = "openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {

        if (openid == null || openid.equals("")) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        Page<OrderDTO> orderDTOPage = orderService.findList(openid, PageRequest.of(page, size));

        return new ResultVO<List<OrderDTO>>().successWithData(orderDTOPage.getContent());
    }

    @RequestMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam(value = "openid") String openid,
                                     @RequestParam(value = "orderId") String orderId) {
        if (openid == null || orderId == null || openid.equals("") || orderId.equals("")) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = orderService.findOne(orderId);

        if (!openid.equals(orderDTO.getBuyerOpenid())) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        return new ResultVO<OrderDTO>().successWithData(orderDTO);
    }

    @RequestMapping("/cancel")
    public ResultVO cancel(@RequestParam(value = "openid") String openid,
                           @RequestParam(value = "orderId") String orderId) {
        ResultVO resultVO;
        if (openid.equals("") || orderId.equals("")) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = orderService.findOne(orderId);
        orderService.cancel(orderDTO);

        return ResultVO.success();
    }
}


