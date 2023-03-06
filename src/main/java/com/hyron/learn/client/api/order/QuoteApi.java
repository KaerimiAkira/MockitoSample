package com.hyron.learn.client.api.order;

import com.hyron.learn.model.client.order.GetQuoteResponse;
import org.springframework.stereotype.Component;

@Component
public class QuoteApi {

  public GetQuoteResponse getQuote(String quoteSlipNumber) {
    throw new RuntimeException("not implementation.");
  }
}
