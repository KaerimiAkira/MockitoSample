package com.hyron.learn.controller.impl;

import com.hyron.learn.controller.SampleApi;
import com.hyron.learn.model.server.sample.CreateUserRequest;
import com.hyron.learn.model.server.sample.CreateUserResponse;
import com.hyron.learn.model.server.sample.GetUserResponse;
import com.hyron.learn.service.CreateUserService;
import com.hyron.learn.service.GetUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController implements SampleApi {
  private final GetUserService getUserService;
  private final CreateUserService createUserService;

  @Override
  public GetUserResponse getUser(String loginId) {
    return getUserService.getUser(loginId);
  }

  @Override
  public GetUserResponse getUser(String loginId, String fromServer) {
    return getUserService.getUser(loginId, "1".equals(fromServer));
  }

  @Override
  public CreateUserResponse createUser(CreateUserRequest request) {
    return createUserService.createUser(request);
  }
}
