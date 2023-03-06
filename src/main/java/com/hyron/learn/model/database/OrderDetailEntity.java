package com.hyron.learn.model.database;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderDetailEntity {
  private String slipNumber;
  private Integer lineNumber;
  private String productCode;
  private Integer qty;
  private BigDecimal unitPrice;
  private BigDecimal salesAmount;
  private String status;
  private ZonedDateTime createdDateTime;
  private ZonedDateTime updatedDateTime;
}
