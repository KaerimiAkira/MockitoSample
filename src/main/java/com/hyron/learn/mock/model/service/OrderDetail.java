package com.hyron.learn.mock.model.service;

import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderDetail {
  private String slipNumber;
  private Integer lineNumber;
  private String productCode;
  private Integer qty;
  private BigDecimal unitPrice;
  private BigDecimal salesAmount;
}
