package com.robbie.shoppingmall.dto;

import com.robbie.shoppingmall.constant.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductQueryParams {
  private String keyword;
  private ProductCategory productCategory;
  private Integer page;
  private Integer limit;
  private String sort;
  private String orderBy;
}
