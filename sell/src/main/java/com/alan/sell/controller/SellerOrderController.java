package com.alan.sell.controller;

import com.alan.sell.dataobject.OrderDetail;
import com.alan.sell.dto.OrderDTO;
import com.alan.sell.enums.OrderStatusEnum;
import com.alan.sell.enums.PayStatusEnum;
import com.alan.sell.enums.ResultEnum;
import com.alan.sell.exception.SellException;
import com.alan.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("pageSize", size);

        return new ModelAndView("order/list", map);
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map) {
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);
            OrderDTO dto = orderService.cancel(orderDTO);
        } catch (Exception se) {
            log.error("【卖家取消订单】错误，Exception={}", se);
            map.put("msg", se.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", "取消订单成功");
        map.put("url", "/sell/seller/order/list");

        return new ModelAndView("common/success", map);
    }

    @GetMapping("/detail")
    public ModelAndView detailList(@RequestParam(value = "orderId") String orderId,
                                   Map<String, Object> map) {
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (Exception se) {
            map.put("msg", se.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam(value = "orderId") String orderId,
                                   Map<String, Object> map) {
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);
            OrderDTO dto = orderService.finish(orderDTO);
        } catch (Exception se) {
            log.error("【卖家完结订单】错误，Exception={}", se);
            map.put("msg", se.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", "完结订单成功");
        map.put("url", "/sell/seller/order/list");

        return new ModelAndView("common/success", map);
    }
}
