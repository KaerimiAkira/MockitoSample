package com.hyron.learn.exception;

import com.hyron.learn.model.value.EnumMessages;

public class SystemException extends BaseException {
  public SystemException(EnumMessages message, Object... arguments) {
    super(message, arguments);
  }

  public SystemException(EnumMessages message, Throwable cause, Object... arguments) {
    super(message, cause, arguments);
  }
}
