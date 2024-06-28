package com.robbie.shoppingmall.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class UsersRequest {
  @NotBlank private String email;
  @NotBlank private String password;
}
