package com.hyron.learn.service;

import com.hyron.learn.client.MasterClient;
import com.hyron.learn.exception.BusinessException;
import com.hyron.learn.mapping.SampleApiMapping;
import com.hyron.learn.model.server.sample.GetUserResponse;
import com.hyron.learn.model.value.Messages;
import com.hyron.learn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserService {
  private final UserRepository userRepository;
  private final SampleApiMapping sampleApiMapping;
  private final MasterClient masterClient;

  public GetUserResponse getUser(String loginId) {
    return getUser(loginId, false);
  }

  public GetUserResponse getUser(String loginId, boolean callApi) {
    var response = new GetUserResponse();
    var userInfo = userRepository.getUserByLoginId(loginId);
    if (callApi) {
      if (userInfo == null) {
        masterClient.getUser(loginId);
      }
    } else {
      if (userInfo == null) {
        throw new BusinessException(Messages.BE0001, loginId);
      }
      sampleApiMapping.mapping(response, userInfo);
    }

    return response;
  }
}
