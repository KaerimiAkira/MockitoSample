package com.hyron.learn.client;

import com.hyron.learn.model.client.order.GetOrderResponse;
import com.hyron.learn.model.client.order.GetQuoteResponse;

public interface OrderClient {

  GetQuoteResponse getQuote(String quoteSlipNumber);

  GetOrderResponse getOrder(String orderSlipNumber);
}
