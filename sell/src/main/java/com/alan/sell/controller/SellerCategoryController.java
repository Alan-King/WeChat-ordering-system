package com.alan.sell.controller;

import com.alan.sell.dataobject.ProductCategory;
import com.alan.sell.dataobject.ProductInfo;
import com.alan.sell.exception.SellException;
import com.alan.sell.form.CategoryForm;
import com.alan.sell.form.ProductForm;
import com.alan.sell.service.CategoryService;
import com.alan.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(HashMap<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        if (categoryId != null) {
            ProductCategory category = categoryService.findOneById(categoryId);
            map.put("category", category);
        }
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productCategoryList", productCategoryList);

        return new ModelAndView("category/index", map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form, BindingResult bindingResult, Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error", map);
        }
        ProductCategory category = null;
        if (form.getCategoryId() != null) {
            category = categoryService.findOneById(form.getCategoryId());
        } else {
            category = new ProductCategory();
        }
        BeanUtils.copyProperties(form,category);
        try {
            categoryService.save(category);
        } catch (SellException se) {
            map.put("msg", se.getMessage());
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", "");
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", map);
    }

}
