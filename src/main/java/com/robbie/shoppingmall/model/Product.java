package com.robbie.shoppingmall.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="product")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name="product_name")
    private String productName;

    @Column(name="category")
    private String category;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="price")
    private Integer price;

    @Column(name="stock")
    private Integer stock;

    @Column(name="description")
    private String description;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="last_modified_date")
    private Date lastModifiedDate;

}
