package com.hyron.learn.mock.exception;

import com.hyron.learn.mock.model.value.EnumMessages;
import java.text.MessageFormat;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
  private final EnumMessages message;
  private final Object[] arguments;
  private final MessageFormat format;

  public BaseException(EnumMessages message, Object... arguments) {
    super();
    this.message = message;
    this.arguments = arguments;
    format = new MessageFormat(message.getDescription());
  }

  public BaseException(EnumMessages message, Throwable cause, Object... arguments) {
    super(cause);
    this.message = message;
    this.arguments = arguments;
    format = new MessageFormat(message.getDescription());
  }

  @Override
  public String getMessage() {
    if (arguments == null || arguments.length == 0) {
      return format.format(new Object[0]);
    } else {
      return format.format(arguments);
    }
  }

  public String getCode() {
    return message.getCode();
  }
}
