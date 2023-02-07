package com.hyron.learn.mock.exception;

import com.hyron.learn.mock.model.value.EnumMessages;

public class BusinessException extends BaseException {
  public BusinessException(EnumMessages message, Object... arguments) {
    super(message, arguments);
  }

  public BusinessException(EnumMessages message, Throwable cause, Object... arguments) {
    super(message, cause, arguments);
  }
}
