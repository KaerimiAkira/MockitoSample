package com.hyron.learn.model.server.sample;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CancelOrderResponse {

  public enum ResultCode {
    OK("0"),
    NG("1"),
    ;

    private final String value;

    ResultCode(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }
  @Data
  @Accessors(chain = true)
  public static class CancelOrderResponseDetail {
    private Integer lineNumber;
    private ResultCode resultCode;
    private ZonedDateTime cancelDateTime;
    private String errorCode;
    private String errorMessage;
  }

  private String slipNumber;
  private ResultCode resultCode;
  private ZonedDateTime cancelDateTime;
  private String errorCode;
  private String errorMessage;
  private List<CancelOrderResponseDetail> detailList = new ArrayList<>();
}
