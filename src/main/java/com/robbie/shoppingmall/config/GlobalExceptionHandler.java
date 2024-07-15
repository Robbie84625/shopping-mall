package com.robbie.shoppingmall.config;

import com.robbie.shoppingmall.common.ErrorInfo;
import com.robbie.shoppingmall.common.ResultError;
import com.robbie.shoppingmall.exceptions.ValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ValidException.class)
  public ResponseEntity<ResultError> handleValidException(ValidException ex) {
    ResultError errorInfo = ex.getResultError();
    return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
  }
}
