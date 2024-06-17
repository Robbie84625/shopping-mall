package com.robbie.shoppingmall.service.impl;

import com.robbie.shoppingmall.dao.ProductRepository;
import com.robbie.shoppingmall.model.Product;
import com.robbie.shoppingmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProductById(Integer id){
        Product product =  productRepository.findById(id).orElse(null);
        return product;
    }
}
