package com.robbie.shoppingmall.service;

import com.robbie.shoppingmall.dto.ProductQueryParams;
import com.robbie.shoppingmall.dto.ProductRequest;
import com.robbie.shoppingmall.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Product getProductById(Integer id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    Map<String, Object> getProducts(ProductQueryParams productQueryParams);
}