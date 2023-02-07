package com.hyron.learn.mock.controller;

import com.hyron.learn.mock.model.server.sample.CreateUserRequest;
import com.hyron.learn.mock.model.server.sample.CreateUserResponse;
import com.hyron.learn.mock.model.server.sample.GetUserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface SampleApi {

  @GetMapping(
      value = "/sample/user/get",
      produces = {"application/json;charset=utf-8"})
  GetUserResponse getUser(@RequestParam("loginId") String loginId);

  @PostMapping(
      value = "/sample/user/create",
      produces = {"application/json;charset=utf-8"},
      consumes = {"application/json;charset=utf-8"})
  CreateUserResponse createUser(@RequestBody CreateUserRequest request);
}
