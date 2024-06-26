package com.robbie.shoppingmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robbie.shoppingmall.constant.ProductCategory;
import com.robbie.shoppingmall.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.support.NullValue;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    //查詢商品
    @Test
    public void getProduct_success() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/product/{productId}",1);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName",equalTo("蘋果（澳洲）")))
                .andExpect(jsonPath("$.category",equalTo("FOOD")))
                .andExpect(jsonPath("$.imageUrl",notNullValue()))
                .andExpect(jsonPath("$.price",notNullValue()))
                .andExpect(jsonPath("$.stock",notNullValue()))
                .andExpect(jsonPath("$.description",notNullValue()))
                .andExpect(jsonPath("$.createdDate",notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate",notNullValue()));
    }

    @Test
    public void getProduct_notFound() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/product/{productId}",20000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    //創建商品
    @Transactional
    @Test
    public void createProduct_success() throws Exception{
        ProductRequest productRequest = ProductRequest
                .builder()
                .productName("test food product")
                .productCategory(ProductCategory.FOOD)
                .imageUrl("http://test.com")
                .price(100)
                .stock(2)
                .build();

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.productName",equalTo("test food product")))
                .andExpect(jsonPath("$.category",equalTo("FOOD")))
                .andExpect(jsonPath("$.imageUrl",equalTo("http://test.com")))
                .andExpect(jsonPath("$.price",equalTo(100)))
                .andExpect(jsonPath("$.stock",equalTo(2)))
                .andExpect(jsonPath("$.description",nullValue()))
                .andExpect(jsonPath("$.createdDate",notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate",notNullValue()));
    }

    @Transactional
    @Test
    public void createProduct_illegalArgument() throws Exception{
        ProductRequest productRequest = ProductRequest
                .builder()
                .productName("test food product")
                .build();

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    //修改商品
    @Transactional
    @Test
    public void updateProduct_success() throws Exception{
        ProductRequest productRequest = ProductRequest
                .builder()
                .productName("test food product")
                .productCategory(ProductCategory.FOOD)
                .imageUrl("http://test.com")
                .price(100)
                .stock(2)
                .build();

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.productName",equalTo("test food product")))
                .andExpect(jsonPath("$.category",equalTo("FOOD")))
                .andExpect(jsonPath("$.imageUrl",equalTo("http://test.com")))
                .andExpect(jsonPath("$.price",equalTo(100)))
                .andExpect(jsonPath("$.stock",equalTo(2)));

    }

    @Transactional
    @Test
    public void updateProduct_illegalArgument() throws Exception{
        ProductRequest productRequest = ProductRequest
                .builder()
                .productName("test food product")
                .build();

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Transactional
    @Test
    public void updateProduct_notFound() throws Exception{
        ProductRequest productRequest = ProductRequest
                .builder()
                .productName("test food product")
                .productCategory(ProductCategory.FOOD)
                .imageUrl("http://test.com")
                .price(100)
                .stock(2)
                .build();

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}",20000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    //刪除商品
    @Transactional
    @Test
    public void deleteProduct_success() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}",5);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    @Transactional
    @Test
    public void deleteNonExistingProduct() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}",20000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    // 查詢商品列表
    @Test
    public void getProducts() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.page", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.result", hasSize(7)));
    }

    @Test
    public void getProducts_filtering() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("keyword", "B")
                .param("category", "CAR");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.page", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.result", hasSize(2)));
    }

    @Test
    public void getProducts_sorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("orderBy", "price")
                .param("sort", "desc");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.page", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.result", hasSize(7)))
                .andExpect(jsonPath("$.result[0].productId", equalTo(6)))
                .andExpect(jsonPath("$.result[1].productId", equalTo(5)))
                .andExpect(jsonPath("$.result[2].productId", equalTo(7)))
                .andExpect(jsonPath("$.result[3].productId", equalTo(4)))
                .andExpect(jsonPath("$.result[4].productId", equalTo(2)));
    }

    @Test
    public void getProducts_pagination() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("limit", "2")
                .param("page", "1");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.page", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.result", hasSize(3)))
                .andExpect(jsonPath("$.result[0].productId", equalTo(4)))
                .andExpect(jsonPath("$.result[1].productId", equalTo(3)));
    }
}