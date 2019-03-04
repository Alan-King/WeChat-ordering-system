package com.alan.sell.service.Impl;

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
import com.alan.sell.repository.ProductInfoRepository;
import com.alan.sell.service.OrderService;
import com.alan.sell.service.ProductService;
import com.alan.sell.utils.KeyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal amount = new BigDecimal(0);
        String orderId = KeyUtils.genUniqueKey();
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
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(amount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4扣库存
        List<CartDTO> cartDtoList = new ArrayList<>(orderDetailList.size());
        cartDtoList = orderDetailList.stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDtoList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageAble) {
        return null;
    }

    @Override
    public OrderDTO cancel(String orderId) {
        return null;
    }

    @Override
    public OrderDTO finish(String orderId) {
        return null;
    }

    @Override
    public OrderDTO paid(String orderId) {
        return null;
    }
}
