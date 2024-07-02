package com.robbie.shoppingmall.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PasswordInfo {
    private final String passwordHash;
    private final String passwordSalt;
}
