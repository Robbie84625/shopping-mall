package com.robbie.shoppingmall.controller;

import com.robbie.shoppingmall.dto.UsersRequest;
import com.robbie.shoppingmall.model.LoginTocken;
import com.robbie.shoppingmall.model.Users;
import com.robbie.shoppingmall.service.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/users/register")
    public ResponseEntity<Users> register(@RequestBody @Valid UsersRequest usersRequest){

        Integer userId = usersService.register(usersRequest);

        Users users = usersService.findUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    public ResponseEntity<LoginTocken> login(@RequestBody @Valid UsersRequest usersRequest){

    }
}
