package com.hyron.learn.mock.service;

import com.hyron.learn.mock.exception.BusinessException;
import com.hyron.learn.mock.model.server.sample.CancelOrderRequest;
import com.hyron.learn.mock.model.server.sample.CancelOrderResponse;
import com.hyron.learn.mock.model.value.Messages;
import com.hyron.learn.mock.model.value.MockEnums;
import com.hyron.learn.mock.repository.OrderRepository;
import com.hyron.learn.mock.utils.DateUtil;
import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelOrderService {
  @Autowired private OrderRepository orderRepository;

  public CancelOrderResponse cancel(CancelOrderRequest request) {

    switch (request.getCompleteFlag()) {
      case CHECK -> {
        return check(request);
      }
      case COMPLETE -> {
        return complete(request);
      }
      default -> throw new BusinessException(Messages.BE0003);
    }
  }

  private CancelOrderResponse check(CancelOrderRequest request) {
    var now = DateUtil.currentSystemDateTime();
    var response =
        new CancelOrderResponse()
            .setSlipNumber(request.getSlipNumber())
            .setResultCode(CancelOrderResponse.ResultCode.OK);
    if (!DateUtil.isOnBusiness(now)) {
      response
          .setResultCode(CancelOrderResponse.ResultCode.NG)
          .setErrorCode(Messages.VE005.getCode())
          .setErrorMessage(Messages.VE005.getDescription());
      return response;
    }
    if (request.getDetailList().isEmpty()) {
      var order = orderRepository.getOrderSlip(request.getSlipNumber());
      if (order.getCreatedDateTime().isBefore(now.minusDays(2))) {
        response
            .setResultCode(CancelOrderResponse.ResultCode.NG)
            .setErrorCode(Messages.VE001.getCode())
            .setErrorMessage(
                MessageFormat.format(Messages.VE001.getDescription(), request.getSlipNumber()));
      } else if (MockEnums.OrderStatus.CANCELED.toString().equals(order.getStatus())) {
        response
            .setResultCode(CancelOrderResponse.ResultCode.NG)
            .setErrorCode(Messages.VE002.getCode())
            .setErrorMessage(
                MessageFormat.format(Messages.VE002.getDescription(), request.getSlipNumber()));
      }
    } else {
      for (var detail : request.getDetailList()) {
        var responseDetail =
            new CancelOrderResponse.CancelOrderResponseDetail()
                .setLineNumber(detail.getLineNumber())
                .setResultCode(CancelOrderResponse.ResultCode.OK);
        var orderDetail =
            orderRepository.getOrderDetail(request.getSlipNumber(), detail.getLineNumber());
        if (orderDetail.getCreatedDateTime().isBefore(now.minusDays(2))) {
          response.setResultCode(CancelOrderResponse.ResultCode.NG);

          responseDetail
              .setResultCode(CancelOrderResponse.ResultCode.NG)
              .setErrorCode(Messages.VE003.getCode())
              .setErrorMessage(
                  MessageFormat.format(
                      Messages.VE003.getDescription(),
                      request.getSlipNumber(),
                      detail.getLineNumber()));

          response.getDetailList().add(responseDetail);
        } else if (MockEnums.OrderStatus.CANCELED.toString().equals(orderDetail.getStatus())) {
          response.setResultCode(CancelOrderResponse.ResultCode.NG);

          responseDetail
              .setResultCode(CancelOrderResponse.ResultCode.NG)
              .setErrorCode(Messages.VE004.getCode())
              .setErrorMessage(
                  MessageFormat.format(
                      Messages.VE004.getDescription(),
                      request.getSlipNumber(),
                      detail.getLineNumber()));
        }
      }
    }
    return response;
  }

  private CancelOrderResponse complete(CancelOrderRequest request) {
    var response = check(request);
    return response;
  }
}
