package com.hyron.learn.mock.controller.impl;

import com.hyron.learn.mock.controller.SampleApi;
import com.hyron.learn.mock.model.server.sample.CreateUserRequest;
import com.hyron.learn.mock.model.server.sample.CreateUserResponse;
import com.hyron.learn.mock.model.server.sample.GetUserResponse;
import com.hyron.learn.mock.service.CreateUserService;
import com.hyron.learn.mock.service.GetUserService;
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
  public CreateUserResponse createUser(CreateUserRequest request) {
    return createUserService.createUser(request);
  }
}
