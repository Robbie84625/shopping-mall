package com.robbie.shoppingmall.service.impl;

import com.robbie.shoppingmall.dao.ProductRepository;
import com.robbie.shoppingmall.dto.ProductQueryParams;
import com.robbie.shoppingmall.dto.ProductRequest;
import com.robbie.shoppingmall.model.Product;
import com.robbie.shoppingmall.service.ProductService;
import com.robbie.shoppingmall.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public PagedResponse<Product> getProducts(ProductQueryParams productQueryParams) {
        // 指定排序方式和排序欄位
        Sort.Direction sortDirection = "asc".equalsIgnoreCase(productQueryParams.getSort()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        String orderBy = productQueryParams.getOrderBy() != null ? productQueryParams.getOrderBy() : "createdDate";
        // 建立分頁和排序的設定
        Pageable pageable = PageRequest.of(productQueryParams.getPage(), productQueryParams.getLimit()+1, Sort.by(sortDirection, orderBy));
        Page<Product> productPage = fetchProducts(productQueryParams,pageable);

        //判斷是最後一頁邏輯
        List<Product> products = new ArrayList<>(productPage.getContent());
        if (products.size() > productQueryParams.getLimit()) {
            products.remove(products.size() - 1);
        }

        return PagedResponse.<Product>builder()
                .page(productQueryParams.getPage())
                .nextPage(productQueryParams.getPage()+1)
                .limit(productQueryParams.getLimit())
                .total(countProducts(productQueryParams))
                .result(fetchProducts(productQueryParams,pageable).getContent())
                .build();
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

    private Page<Product> fetchProducts(ProductQueryParams productQueryParams,Pageable pageable){
        if (productQueryParams.getProductCategory() != null
                && productQueryParams.getKeyword() != null
                && !productQueryParams.getKeyword().trim().isEmpty()
        ) {
            // 同時按類別和關鍵字搜索
            return productRepository.findByCategoryAndProductNameContaining(productQueryParams.getProductCategory(), productQueryParams.getKeyword(), pageable);
        } else if (productQueryParams.getProductCategory() != null) {
            // 只按類別搜索
            return productRepository.findByCategory(productQueryParams.getProductCategory(), pageable);
        } else if (productQueryParams.getKeyword() != null && !productQueryParams.getKeyword().trim().isEmpty()) {
            // 只按關鍵字搜索
            return productRepository.findByProductNameContaining(productQueryParams.getKeyword(), pageable);
        } else {
            // 沒有搜索條件，返回所有產品
            return productRepository.findAll(pageable);
        }
    }

    private long countProducts(ProductQueryParams productQueryParams) {
        if (productQueryParams.getProductCategory() != null
                && productQueryParams.getKeyword() != null
                && !productQueryParams.getKeyword().trim().isEmpty()
        ) {
            return productRepository.countByCategoryAndProductNameContaining(productQueryParams.getProductCategory(), productQueryParams.getKeyword());
        } else if (productQueryParams.getProductCategory() != null) {
            return productRepository.countByCategory(productQueryParams.getProductCategory());
        } else if (productQueryParams.getKeyword() != null && !productQueryParams.getKeyword().trim().isEmpty()) {
            return productRepository.countByProductNameContaining(productQueryParams.getKeyword());
        } else {
            return productRepository.count();
        }
    }
}
