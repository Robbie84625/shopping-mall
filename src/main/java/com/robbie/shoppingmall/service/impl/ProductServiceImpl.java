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

    private enum QueryType {
        CATEGORY_AND_KEYWORD, CATEGORY_ONLY, KEYWORD_ONLY, NONE
    }

    private QueryType determineQueryType(ProductQueryParams productQueryParams){
        boolean hasCategory = productQueryParams.getProductCategory() != null;
        boolean hasKeyword = productQueryParams.getKeyword() != null;

        if(hasCategory && hasKeyword){
            return QueryType.CATEGORY_AND_KEYWORD;
        } else if (hasCategory) {
            return QueryType.CATEGORY_ONLY;
        } else if (hasKeyword) {
            return QueryType.KEYWORD_ONLY;
        }else {
            return QueryType.NONE;
        }
    }

    private Page<Product> fetchProducts(ProductQueryParams productQueryParams,Pageable pageable){
        QueryType queryType = determineQueryType(productQueryParams);

        switch (queryType){
            case CATEGORY_AND_KEYWORD:
                return productRepository.findByCategoryAndProductNameContaining(productQueryParams.getProductCategory(), productQueryParams.getKeyword(), pageable);
            case CATEGORY_ONLY:
                return productRepository.findByCategory(productQueryParams.getProductCategory(), pageable);
            case KEYWORD_ONLY:
                return productRepository.findByProductNameContaining(productQueryParams.getKeyword(), pageable);
            default:
                return productRepository.findAll(pageable);
        }
    }

    private long countProducts(ProductQueryParams productQueryParams) {
        QueryType queryType = determineQueryType(productQueryParams);

        switch (queryType) {
            case CATEGORY_AND_KEYWORD:
                return productRepository.countByCategoryAndProductNameContaining(productQueryParams.getProductCategory(), productQueryParams.getKeyword());
            case CATEGORY_ONLY:
                return productRepository.countByCategory(productQueryParams.getProductCategory());
            case KEYWORD_ONLY:
                return productRepository.countByProductNameContaining(productQueryParams.getKeyword());
            default:
                return productRepository.count();
        }
    }
}
