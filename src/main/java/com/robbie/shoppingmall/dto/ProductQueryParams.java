package com.robbie.shoppingmall.dto;

import com.robbie.shoppingmall.constant.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductQueryParams {
    private String keyword;
    private ProductCategory productCategory;
    private Integer page;/
}
