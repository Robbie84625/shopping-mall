package com.robbie.shoppingmall.service;

import com.robbie.shoppingmall.constant.ProductCategory;
import com.robbie.shoppingmall.dto.ProductRequest;
import com.robbie.shoppingmall.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Integer id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);


    List<Product> getProducts(int page, ProductCategory productCategory);
}