package com.robbie.shoppingmall.service.interfaces;

import com.robbie.shoppingmall.dto.UsersRequest;
import com.robbie.shoppingmall.exceptions.ValidException;
import com.robbie.shoppingmall.model.LoginTocken;
import com.robbie.shoppingmall.model.Users;

public interface UsersService {

  Users findUserById(Integer userId);

  LoginTocken login(UsersRequest usersRequest) throws ValidException;

  Integer register(UsersRequest usersRequest) throws ValidException;
}
