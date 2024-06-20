package com.robbie.shoppingmall.model;

import com.robbie.shoppingmall.constant.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name="product_name")
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name="category")
    private ProductCategory category;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="price")
    private Integer price;

    @Column(name="stock")
    private Integer stock;

    @Column(name="description", nullable = true)
    private String description;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="last_modified_date")
    private Date lastModifiedDate;

}
