package com.hyron.learn.model.database;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderEntity {
  private String slipNumber;
  private String customerCode;
  private String chargeId;
  private BigDecimal totalAmount;
  private String status;
  private ZonedDateTime createdDateTime;
  private ZonedDateTime updatedDateTime;
}
