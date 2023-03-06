package com.hyron.learn.client.api.order;

import com.hyron.learn.model.client.order.GetOrderResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderApi {
  public GetOrderResponse getOrder(String orderSlipNumber) {
    throw new RuntimeException("not implementation.");
  }
}
