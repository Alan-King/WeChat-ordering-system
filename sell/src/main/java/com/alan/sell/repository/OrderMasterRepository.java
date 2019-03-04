package com.alan.sell.repository;

import com.alan.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    public Page<OrderMaster> findOrderListByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
