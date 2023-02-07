package com.hyron.learn.mock.model.server.sample;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateUserResponse {
  private String loginId;
  private String userName;
  private String mailAddress;
  private String address;
  private ZonedDateTime createdDateTime;
  private ZonedDateTime updatedDateTime;
}
