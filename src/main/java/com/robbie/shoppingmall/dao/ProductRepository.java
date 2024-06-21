package com.robbie.shoppingmall.dao;

import com.robbie.shoppingmall.constant.ProductCategory;
import com.robbie.shoppingmall.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Integer> {
    Page<Product> findByCategory(ProductCategory category, Pageable pageable);
}
