package com.alan.sell.service.Impl;

import com.alan.sell.converter.OrderDTO2PayRequestConverter;
import com.alan.sell.dto.OrderDTO;
import com.alan.sell.enums.PayStatusEnum;
import com.alan.sell.enums.ResultEnum;
import com.alan.sell.exception.SellException;
import com.alan.sell.service.OrderService;
import com.alan.sell.service.PayService;
import com.alan.sell.utils.JsonUtils;
import com.alan.sell.utils.MathUtils;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest request = OrderDTO2PayRequestConverter.convert(orderDTO);
        log.info("【微信支付】 发起支付，request={}", JsonUtils.toJSON(request));
        PayResponse payResponse = bestPayService.pay(request);
        log.info("【微信支付】 发起支付，payResponse={}", JsonUtils.toJSON(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1验证签名 （bestPayService中已经验证）
        //2验证支付状态 （bestPayService中已经验证）
        //3支付金额
        //4支付人是否等于下单人（此项目不限制）
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】 异步通知，payResponse={}", JsonUtils.toJSON(payResponse));
        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        //查询订单是否存在
        if (orderDTO == null) {
            log.error("【微信支付】 异步通知，订单不存在");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //查询金额是否一致
        if (!MathUtils.equals(orderDTO.getOrderAmount().doubleValue(), payResponse.getOrderAmount())) {
            log.error("【微信支付】 异步通知，订单金额不一致，orderId={},微信通知金额={},系统订单金额={}",
                    orderDTO.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        //修改支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            orderService.paid(orderDTO);
        }
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest request = new RefundRequest();
        request.setOrderId(orderDTO.getOrderId());
        request.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】request={}", JsonUtils.toJSON(request));
        RefundResponse refundResponse = bestPayService.refund(request);
        log.info("【微信退款】RefundResponse={}", JsonUtils.toJSON(refundResponse));
        return refundResponse;
    }
}
