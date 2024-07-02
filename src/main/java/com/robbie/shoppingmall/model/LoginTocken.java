package com.robbie.shoppingmall.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginTocken {
    private String token;
    private String type = "Bearer";
    private String email;
}
