package com.alan.sell.repository;

import com.alan.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    public List<OrderDetail> findDetailListByOrderId(String orderId);
}
