package com.robbie.shoppingmall.service.impl;

import com.robbie.shoppingmall.dao.ProductRepository;
import com.robbie.shoppingmall.dto.ProductRequest;
import com.robbie.shoppingmall.model.Product;
import com.robbie.shoppingmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProductById(Integer id){
        Product product =  productRepository.findById(id).orElse(null);
        return product;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        Date now = new Date();


        Product product = new Product(
                null,
            productRequest.getProductName(),
            productRequest.getProductCategory(),
            productRequest.getImageUrl(),
            productRequest.getPrice(),
            productRequest.getStock(),
            productRequest.getDescription(),
                now,
                now
        );
        if (product != null) {
            Product savedProduct = productRepository.save(product);
            return savedProduct.getProductId();
        }
        return null;
    }
}
