package com.alan.sell.service.Impl;

import com.alan.sell.converter.OrderMaster2OrderDTOConverter;
import com.alan.sell.dataobject.OrderDetail;
import com.alan.sell.dataobject.OrderMaster;
import com.alan.sell.dataobject.ProductInfo;
import com.alan.sell.dto.CartDTO;
import com.alan.sell.dto.OrderDTO;
import com.alan.sell.enums.OrderStatusEnum;
import com.alan.sell.enums.PayStatusEnum;
import com.alan.sell.enums.ResultEnum;
import com.alan.sell.exception.SellException;
import com.alan.sell.repository.OrderDetailRepository;
import com.alan.sell.repository.OrderMasterRepository;
import com.alan.sell.service.OrderService;
import com.alan.sell.service.PayService;
import com.alan.sell.service.ProductService;
import com.alan.sell.service.WebSocketService;
import com.alan.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderMasterRepository orderMasterRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;

    @Autowired
    private PayService payService;

    @Autowired
    WebSocketService webSocketService;

    @Autowired
    public OrderServiceImpl(OrderMasterRepository orderMasterRepository, OrderDetailRepository orderDetailRepository, ProductService productService) {
        this.orderMasterRepository = orderMasterRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal amount = new BigDecimal(0);
        String orderId = KeyUtils.genUniqueKey();
        orderDTO.setOrderId(orderId);
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        //1查询商品（数量。价格）
        for (OrderDetail orderDetail : orderDetailList) {
            ProductInfo productInfo = productService.findOneById(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2计算总价
            amount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(amount);

            //3写入数据库
            //使用BeanUtils.copyProperties方法时 为null值也会被拷贝过去，所以应该先进行拷贝然后再赋值
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtils.genUniqueKey());
            orderDetailRepository.save(orderDetail);
        }

        //OrderMaster写入数据库
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(amount);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());

        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMasterRepository.save(orderMaster);

        //4扣库存
        List<CartDTO> cartDtoList = new ArrayList<>(orderDetailList.size());
        cartDtoList = orderDetailList.stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDtoList);

        //WebSocket发送消息
        webSocketService.sendMessage(orderId);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findDetailListByOrderId(orderId);
        if (orderDetailList.isEmpty()) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageAble) {

        Page<OrderMaster> orderMasterPage = orderMasterRepository.findOrderListByBuyerOpenid(buyerOpenId, pageAble);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converterList(orderMasterPage.getContent());

      /*  for (OrderDTO orderDTO : orderDTOList) {
            List<OrderDetail> detailList = orderDetailRepository.findDetailListByOrderId(orderDTO.getOrderId());
            orderDTO.setOrderDetailList(detailList);
        }*/

        return new PageImpl<>(orderDTOList, pageAble, orderMasterPage.getTotalElements());
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageAble) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageAble);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converterList(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageAble,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        //查询订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态错误 {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_WRONG);
        }

        //更改数据库（状态，库存）
        orderDTO.setOrderStatus(OrderStatusEnum.CANCLED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster = orderMasterRepository.save(orderMaster);
        List<OrderDetail> detailList = orderDTO.getOrderDetailList();
        if (CollectionUtils.isEmpty(detailList)) {
            log.error("【取消订单】订单中无商品详情 {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        List<CartDTO> cartDTOList = detailList.stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //查看支付状态（返还已支付钱）
        Integer payStatus = orderMaster.getPayStatus();
        if (payStatus.equals(PayStatusEnum.SUCCESS.getCode())) {
            payService.refund(orderDTO);
        }

        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {

        //查询订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完成订单】订单状态错误 {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_WRONG);
        }

        //更改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster = orderMasterRepository.save(orderMaster);
        if (orderMaster == null) {
            log.error("【完成订单】失败,orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_FINNISH_FAIL);
        }

        List<OrderDetail> detailList = orderDTO.getOrderDetailList();
        if (CollectionUtils.isEmpty(detailList)) {
            log.error("【完成订单】订单中无商品详情 {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {

        //查询订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【支付订单】订单状态错误 {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_WRONG);
        }
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【支付订单】支付状态错误 {}", orderDTO);
            throw new SellException(ResultEnum.PAY_STATUS_WRONG);
        }

        List<OrderDetail> detailList = orderDTO.getOrderDetailList();
        if (CollectionUtils.isEmpty(detailList)) {
            log.error("【支付订单】订单中无商品详情 {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        //更改支付状态
        orderMaster = orderMasterRepository.save(orderMaster);

        return orderDTO;
    }
}
