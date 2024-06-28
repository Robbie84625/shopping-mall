package com.robbie.shoppingmall.service.impl;

import com.robbie.shoppingmall.dao.UsersRepository;
import com.robbie.shoppingmall.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;
}
