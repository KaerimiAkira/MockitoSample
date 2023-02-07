package com.hyron.learn.mock.service;

import com.hyron.learn.mock.exception.BusinessException;
import com.hyron.learn.mock.mapping.SampleApiMapping;
import com.hyron.learn.mock.model.server.sample.GetUserResponse;
import com.hyron.learn.mock.model.value.Messages;
import com.hyron.learn.mock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserService {
  private final UserRepository userRepository;
  private final SampleApiMapping sampleApiMapping;

  public GetUserResponse getUser(String loginId) {
    var userInfo = userRepository.getUserByLoginId(loginId);

    if (userInfo == null) {
      throw new BusinessException(Messages.BE0001, loginId);
    }

    var response = new GetUserResponse();
    sampleApiMapping.mapping(response, userInfo);

    return response;
  }
}
