package com.robbie.shoppingmall.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
@ToString
public class ResultError implements Serializable {
    private final String errorCode;

    private final String errorMessage;
}
