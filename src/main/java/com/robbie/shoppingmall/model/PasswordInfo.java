package com.robbie.shoppingmall.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class PasswordInfo {
    private final String passwordHash;
    private final String passwordSalt;
}
