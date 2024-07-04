package com.robbie.shoppingmall.service.interfaces;

import com.robbie.shoppingmall.dto.UsersRequest;
import com.robbie.shoppingmall.model.LoginTocken;
import com.robbie.shoppingmall.model.Users;

public interface UsersService {
    public Integer register(UsersRequest usersRequest);

    public Users findUserById(Integer userId);

    public LoginTocken login(UsersRequest usersRequest);
}
