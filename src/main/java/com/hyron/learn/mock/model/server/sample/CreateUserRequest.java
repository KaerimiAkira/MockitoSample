package com.hyron.learn.mock.model.server.sample;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateUserRequest {
  private String loginId;
  private String userName;
  private String mailAddress;
  private String address;
}
