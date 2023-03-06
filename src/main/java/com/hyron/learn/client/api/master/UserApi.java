package com.hyron.learn.client.api.master;

import com.hyron.learn.model.client.master.GetUserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserApi {

  public GetUserResponse getUser(String userId) {
    throw new RuntimeException("not implementation.");
  }
}
