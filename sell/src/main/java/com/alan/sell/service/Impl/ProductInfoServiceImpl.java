package com.alan.sell.service.Impl;

import com.alan.sell.dataobject.ProductInfo;
import com.alan.sell.dto.CartDTO;
import com.alan.sell.enums.ProductStatusEnum;
import com.alan.sell.enums.ResultEnum;
import com.alan.sell.exception.SellException;
import com.alan.sell.repository.ProductInfoRepository;
import com.alan.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOneById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer newStock = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (newStock < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_NOT_ENOUGH);
            }
            productInfo.setProductStock(newStock);
            repository.save(productInfo);
        }
    }
}
