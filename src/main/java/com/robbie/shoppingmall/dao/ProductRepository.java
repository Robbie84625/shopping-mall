package com.robbie.shoppingmall.dao;

import com.robbie.shoppingmall.constant.ProductCategory;
import com.robbie.shoppingmall.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Integer> {
    Page<Product> findByCategory(ProductCategory category, Pageable pageable);
    Page<Product> findByProductNameContaining(String keyword,Pageable pageable);
    Page<Product> findByCategoryAndProductNameContaining(ProductCategory category,String keyword,Pageable pageable);
}
