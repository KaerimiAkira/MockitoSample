package com.hyron.learn.repository;

import com.hyron.learn.model.database.OrderDetailEntity;
import com.hyron.learn.model.database.OrderEntity;
import java.util.List;

public interface OrderRepository {
  OrderEntity getOrderSlip(String slipNumber);

  List<OrderDetailEntity> getAllDetail(String slipNumber);

  OrderDetailEntity getOrderDetail(String slipNumber, Integer lineNumber);
}
