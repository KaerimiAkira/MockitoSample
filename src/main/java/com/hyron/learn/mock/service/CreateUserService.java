package com.hyron.learn.mock.service;

import com.hyron.learn.mock.exception.BusinessException;
import com.hyron.learn.mock.mapping.SampleApiMapping;
import com.hyron.learn.mock.model.database.UserEntity;
import com.hyron.learn.mock.model.server.sample.CreateUserRequest;
import com.hyron.learn.mock.model.server.sample.CreateUserResponse;
import com.hyron.learn.mock.model.value.Messages;
import com.hyron.learn.mock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService {
  private final UserRepository userRepository;
  private final SampleApiMapping sampleApiMapping;

  public CreateUserResponse createUser(CreateUserRequest request) {
    var userInfo = new UserEntity();
    sampleApiMapping.mapping(userInfo, request);
    userInfo = userRepository.createUser(userInfo);

    if (userInfo == null || userInfo.getUserId() == null) {
      throw new BusinessException(Messages.BE0002, request.getLoginId());
    }

    var response = new CreateUserResponse();
    sampleApiMapping.mapping(response, userInfo);

    return response;
  }
}
