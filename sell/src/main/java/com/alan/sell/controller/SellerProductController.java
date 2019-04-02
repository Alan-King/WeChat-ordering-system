package com.alan.sell.controller;

import com.alan.sell.dataobject.ProductCategory;
import com.alan.sell.dataobject.ProductInfo;
import com.alan.sell.exception.SellException;
import com.alan.sell.form.ProductForm;
import com.alan.sell.service.CategoryService;
import com.alan.sell.service.ProductService;
import com.alan.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageable);
        map.put("currentPage", page);
        map.put("pageSize", size);
        map.put("productInfoPage", productInfoPage);

        return new ModelAndView("product/list", map);
    }

    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId, Map<String, Object> map) {

        try {
            ProductInfo productInfo = productService.onSale(productId);
            map.put("productInfo", productInfo);
            return new ModelAndView("product/list", map);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId, Map<String, Object> map) {
        try {
            ProductInfo productInfo = productService.offSale(productId);
            map.put("url", "/sell/seller/product/list");
            map.put("msg", "商品下架成功");
            return new ModelAndView("common/success", map);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOneById(productId);
            map.put("productInfo", productInfo);
        }
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productCategoryList", productCategoryList);

        return new ModelAndView("product/index", map);
    }

    /**
     * 保存、更新
     *
     * @param form
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form, BindingResult bindingResult, Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        ProductInfo productInfo = null;
        if (StringUtils.isEmpty(form.getProductId())) {
            productInfo = new ProductInfo();
            form.setProductId(KeyUtils.genUniqueKey());
        } else {
            productInfo = productService.findOneById(form.getProductId());
        }
        BeanUtils.copyProperties(form,productInfo);
        try {
            productService.save(productInfo);
        } catch (SellException se) {
            map.put("msg", se.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", "");
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

}