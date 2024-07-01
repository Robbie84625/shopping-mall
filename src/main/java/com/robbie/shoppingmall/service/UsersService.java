package com.robbie.shoppingmall.service;

import com.robbie.shoppingmall.dto.UsersRequest;
import com.robbie.shoppingmall.model.Users;

public interface UsersService {
    public Integer register(UsersRequest usersRequest);

    public Users findUserById(Integer userId);
}
