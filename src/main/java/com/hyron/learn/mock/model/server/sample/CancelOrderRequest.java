package com.hyron.learn.mock.model.server.sample;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CancelOrderRequest {
  @Data
  @Accessors(chain = true)
  public static class CancelOrderRequestDetail {
    private Integer lineNumber;
  }

  public enum CompleteFlag {
    CHECK("1"),
    COMPLETE("2"),
    ;

    private final String value;

    CompleteFlag(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

  private String slipNumber;
  private CompleteFlag completeFlag;
  private List<CancelOrderRequestDetail> detailList = new ArrayList<>();
}
