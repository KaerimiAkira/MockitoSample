package com.hyron.learn.client.impl;

import com.hyron.learn.client.OrderClient;
import com.hyron.learn.client.api.order.OrderApi;
import com.hyron.learn.client.api.order.QuoteApi;
import com.hyron.learn.model.client.order.GetOrderResponse;
import com.hyron.learn.model.client.order.GetQuoteResponse;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class OrderClientImpl implements OrderClient {
  private final QuoteApi quoteApi;
  private final OrderApi orderApi;

  @Override
  public GetQuoteResponse getQuote(String quoteSlipNumber) {
    return quoteApi.getQuote(quoteSlipNumber);
  }

  @Override
  public GetOrderResponse getOrder(String orderSlipNumber) {
    return orderApi.getOrder(orderSlipNumber);
  }
}
