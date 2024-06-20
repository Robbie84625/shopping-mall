package com.robbie.shoppingmall.service;

import com.robbie.shoppingmall.dto.ProductRequest;
import com.robbie.shoppingmall.model.Product;

public interface ProductService {
    Product getProductById(Integer id);
    Integer createProduct(ProductRequest productRequest);
}
