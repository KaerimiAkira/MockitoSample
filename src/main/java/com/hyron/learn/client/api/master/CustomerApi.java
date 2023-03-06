package com.hyron.learn.client.api.master;

import com.hyron.learn.model.client.master.GetCustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerApi {
  public GetCustomerResponse getCustomer(String customerCode) {
    throw new RuntimeException("not implementation.");
  }
}
