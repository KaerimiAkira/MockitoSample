package com.hyron.learn.repository.impl;

import com.hyron.learn.model.database.OrderDetailEntity;
import com.hyron.learn.model.database.OrderEntity;
import com.hyron.learn.model.value.MockEnums;
import com.hyron.learn.repository.OrderRepository;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class DefaultOrderRepository implements OrderRepository {
  private static final ZonedDateTime dummy =
      ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.of("+0800"));

  @Override
  public OrderEntity getOrderSlip(String slipNumber) {
    return new OrderEntity()
        .setSlipNumber(slipNumber)
        .setCustomerCode("XXXXXX")
        .setChargeId("XXXXXX")
        .setTotalAmount(BigDecimal.ZERO)
        .setStatus(MockEnums.OrderStatus.ORDERED.toString())
        .setCreatedDateTime(dummy)
        .setUpdatedDateTime(dummy);
  }

  @Override
  public List<OrderDetailEntity> getAllDetail(String slipNumber) {
    return Arrays.asList(
        new OrderDetailEntity()
            .setSlipNumber(slipNumber)
            .setLineNumber(1)
            .setProductCode("P0001")
            .setQty(1)
            .setUnitPrice(BigDecimal.ZERO)
            .setSalesAmount(BigDecimal.ZERO)
            .setStatus(MockEnums.OrderStatus.ORDERED.toString())
            .setCreatedDateTime(dummy)
            .setUpdatedDateTime(dummy),
        new OrderDetailEntity()
            .setSlipNumber(slipNumber)
            .setLineNumber(2)
            .setProductCode("P0002")
            .setQty(1)
            .setUnitPrice(BigDecimal.ZERO)
            .setSalesAmount(BigDecimal.ZERO)
            .setStatus(MockEnums.OrderStatus.ORDERED.toString())
            .setCreatedDateTime(dummy)
            .setUpdatedDateTime(dummy));
  }

  @Override
  public OrderDetailEntity getOrderDetail(String slipNumber, Integer lineNumber) {
    return new OrderDetailEntity()
        .setSlipNumber(slipNumber)
        .setLineNumber(lineNumber)
        .setProductCode("P000X")
        .setQty(1)
        .setUnitPrice(BigDecimal.ZERO)
        .setSalesAmount(BigDecimal.ZERO)
        .setStatus(MockEnums.OrderStatus.ORDERED.toString())
        .setCreatedDateTime(dummy)
        .setUpdatedDateTime(dummy);
  }
}
