package com.robbie.shoppingmall.dao;

import com.robbie.shoppingmall.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Integer> {
}
