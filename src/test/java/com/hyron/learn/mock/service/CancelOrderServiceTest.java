package com.hyron.learn.mock.service;

import com.hyron.learn.mock.TestSupport;
import com.hyron.learn.mock.model.server.sample.CancelOrderRequest;
import com.hyron.learn.mock.model.server.sample.CancelOrderResponse;
import com.hyron.learn.mock.model.value.Messages;
import com.hyron.learn.mock.repository.OrderRepository;
import com.hyron.learn.mock.repository.impl.DefaultOrderRepository;
import com.hyron.learn.mock.utils.DateUtil;
import java.text.MessageFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;

@DisplayName("CancelOrderService Test")
@Slf4j
class CancelOrderServiceTest extends TestSupport {
  @Spy private final OrderRepository orderRepository = new DefaultOrderRepository();

  @InjectMocks private CancelOrderService target;

  @Test
  @DisplayName("cancel check - without mock - check NG")
  void test_case001() {
    var response =
        target.cancel(
            new CancelOrderRequest()
                .setSlipNumber("SO00001")
                .setCompleteFlag(CancelOrderRequest.CompleteFlag.CHECK));

    Assertions.assertThat(response)
        .isNotNull()
        .hasFieldOrPropertyWithValue("slipNumber", "SO00001")
        .hasFieldOrPropertyWithValue("resultCode", CancelOrderResponse.ResultCode.NG)
        .hasFieldOrPropertyWithValue("errorCode", Messages.VE001.getCode())
        .hasFieldOrPropertyWithValue(
            "errorMessage", MessageFormat.format(Messages.VE001.getDescription(), "SO00001"));
  }

  @Test
  @DisplayName("cancel check - static mock - check NG")
  void test_case002() {
    CancelOrderResponse response = null;
    try (var mockedDateUtil = Mockito.mockStatic(DateUtil.class, InvocationOnMock::callRealMethod)) {
      mockedDateUtil.when(() -> DateUtil.isOnBusiness(Mockito.any())).thenReturn(false);

      response =
          target.cancel(
              new CancelOrderRequest()
                  .setSlipNumber("SO00001")
                  .setCompleteFlag(CancelOrderRequest.CompleteFlag.CHECK));
    }

    Assertions.assertThat(response)
        .isNotNull()
        .hasFieldOrPropertyWithValue("slipNumber", "SO00001")
        .hasFieldOrPropertyWithValue("resultCode", CancelOrderResponse.ResultCode.NG)
        .hasFieldOrPropertyWithValue("errorCode", Messages.VE005.getCode())
        .hasFieldOrPropertyWithValue("errorMessage", Messages.VE005.getDescription());
  }

  @Test
  @DisplayName("cancel check - static mock - check OK")
  void test_case003() {
    CancelOrderResponse response = null;
    try (var mockedDateUtil = Mockito.mockStatic(DateUtil.class, InvocationOnMock::callRealMethod)) {
      mockedDateUtil.when(DateUtil::currentSystemDateTime).thenReturn(ZonedDateTime.of(2023, 1, 2, 8, 0, 0, 0, ZoneId.of("+0800")));

      response = target.cancel(new CancelOrderRequest()
          .setSlipNumber("SO00001")
          .setCompleteFlag(CancelOrderRequest.CompleteFlag.CHECK));
    }

    Assertions.assertThat(response)
        .isNotNull()
        .hasFieldOrPropertyWithValue("slipNumber", "SO00001")
        .hasFieldOrPropertyWithValue("resultCode", CancelOrderResponse.ResultCode.OK)
        .hasFieldOrPropertyWithValue("errorCode", null)
        .hasFieldOrPropertyWithValue("errorMessage", null);
  }
}
