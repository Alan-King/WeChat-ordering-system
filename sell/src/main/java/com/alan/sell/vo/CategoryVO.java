package com.alan.sell.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class CategoryVO {

    @JSONField(name="name")
    private String categoryName;

    @JSONField(name="type")
    private Integer categoryType;

    @JSONField(name="foods")
    private List<ProductVO> productVOList;
}
