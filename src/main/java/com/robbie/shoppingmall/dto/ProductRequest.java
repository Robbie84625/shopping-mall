package com.robbie.shoppingmall.dto;

import com.robbie.shoppingmall.constant.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class ProductRequest {

  @NotNull private String productName;
  @NotNull private ProductCategory productCategory;
  @NotNull private String imageUrl;
  @NotNull private Integer price;
  @NotNull private Integer stock;

  private String description;
}
