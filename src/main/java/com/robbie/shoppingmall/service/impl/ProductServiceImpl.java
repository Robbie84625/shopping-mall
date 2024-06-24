package com.robbie.shoppingmall.service.impl;

import com.robbie.shoppingmall.constant.ProductCategory;
import com.robbie.shoppingmall.dao.ProductRepository;
import com.robbie.shoppingmall.dto.ProductRequest;
import com.robbie.shoppingmall.model.Product;
import com.robbie.shoppingmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts(int page, ProductCategory productCategory,String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Product> productPage;

        if (productCategory != null && keyword != null && !keyword.trim().isEmpty()) {
            // 同時按類別和關鍵字搜索
            productPage = productRepository.findByCategoryAndProductNameContaining(productCategory, keyword, pageable);
        } else if (productCategory != null) {
            // 只按類別搜索
            productPage = productRepository.findByCategory(productCategory, pageable);
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            // 只按關鍵字搜索
            productPage = productRepository.findByProductNameContaining(keyword, pageable);
        } else {
            // 沒有搜索條件，返回所有產品
            productPage = productRepository.findAll(pageable);
        }

        return productPage.getContent();
    }

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

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        Date now = new Date();

        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            // 更新所有字段
            existingProduct.setProductName(productRequest.getProductName());
            existingProduct.setCategory(productRequest.getProductCategory());
            existingProduct.setImageUrl(productRequest.getImageUrl());
            existingProduct.setPrice(productRequest.getPrice());
            existingProduct.setStock(productRequest.getStock());

            // 更新 description，如果它不為空
            if (productRequest.getDescription() != null) {
                existingProduct.setDescription(productRequest.getDescription());
            }

            existingProduct.setLastModifiedDate(now);

            productRepository.save(existingProduct);
        }
    }

    @Override
    public void deleteProductById(Integer productId) {
        productRepository.deleteById(productId);
    }
}
