package com.hyron.learn.model.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderSlip {
  private String slipNumber;
  private String customerCode;
  private String chargeId;
  private BigDecimal totalAmount;
  private List<OrderDetail> detailList = new ArrayList<>();
}
