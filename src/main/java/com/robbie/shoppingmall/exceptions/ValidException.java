package com.robbie.shoppingmall.exceptions;

import com.robbie.shoppingmall.common.ErrorInfo;
import com.robbie.shoppingmall.common.ResultError;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;
import java.util.Optional;

@Getter
public class ValidException extends Exception {
  private final ResultError resultError;

  public ValidException(ErrorInfo error, String... messages) {
    this.resultError = toResultError(error, messages);
  }

  @Override
  public String getMessage() {
    return Optional.ofNullable(resultError).map(ResultError::toString).orElse("");
  }

  private ResultError toResultError(ErrorInfo error, String... messages) {
    String errorMessage = error.getErrorMessage();
    if (ArrayUtils.isNotEmpty(messages)) {
      MessageFormat fmt = new MessageFormat(errorMessage);
      errorMessage = fmt.format(messages);
    }
    return new ResultError(error.getErrorCode(), errorMessage);
  }
}
