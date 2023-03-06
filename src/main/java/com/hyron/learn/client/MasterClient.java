package com.hyron.learn.client;

import com.hyron.learn.model.client.master.GetCustomerResponse;
import com.hyron.learn.model.client.master.GetUserResponse;

public interface MasterClient {

  GetUserResponse getUser(String userId);

  GetCustomerResponse getCustomer(String customerCode);
}
