package com.hyron.learn.client.impl;

import com.hyron.learn.client.MasterClient;
import com.hyron.learn.client.api.master.CustomerApi;
import com.hyron.learn.client.api.master.UserApi;
import com.hyron.learn.model.client.master.GetCustomerResponse;
import com.hyron.learn.model.client.master.GetUserResponse;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class MasterClientImpl implements MasterClient {
  private final CustomerApi customerApi;
  private final UserApi userApi;

  @Override
  public GetUserResponse getUser(String userId) {
    return userApi.getUser(userId);
  }

  @Override
  public GetCustomerResponse getCustomer(String customerCode) {
    return customerApi.getCustomer(customerCode);
  }
}
