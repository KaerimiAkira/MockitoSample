package com.hyron.learn.mock.model.value;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Errors {
  private String code;
  private String message;
  private Object[] arguments;
}
