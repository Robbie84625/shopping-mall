package com.robbie.shoppingmall.service.interfaces;

import com.robbie.shoppingmall.dto.UsersRequest;
import com.robbie.shoppingmall.exceptions.ValidException;
import com.robbie.shoppingmall.model.LoginTocken;
import com.robbie.shoppingmall.model.Users;

public interface UsersService {
  public Integer register(UsersRequest usersRequest) throws ValidException; ;

  public Users findUserById(Integer userId);

  public LoginTocken login(UsersRequest usersRequest) throws ValidException;
}
