package com.hyron.learn.configuration;

import com.hyron.learn.exception.BusinessException;
import com.hyron.learn.exception.SystemException;
import com.hyron.learn.model.value.Errors;
import com.hyron.learn.model.value.Messages;
import java.text.MessageFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

  private ResponseEntity<Errors> businessExceptionHandler(BusinessException ex) {
    return new ResponseEntity<>(
        new Errors()
            .setCode(ex.getCode())
            .setMessage(ex.getMessage())
            .setArguments(ex.getArguments()),
        HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<Errors> systemExceptionHandler(SystemException ex) {
    return new ResponseEntity<>(
        new Errors()
            .setCode(ex.getCode())
            .setMessage(ex.getMessage())
            .setArguments(ex.getArguments()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<Errors> otherExceptionHandler(Exception ex) {
    return new ResponseEntity<>(
        new Errors()
            .setCode(Messages.SE0001.getCode())
            .setMessage(MessageFormat.format(Messages.SE0001.getDescription(), ex.getMessage())),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseEntity<Errors> exceptionHandler(Exception ex) {
    if (ex instanceof BusinessException) {
      return businessExceptionHandler((BusinessException) ex);
    } else if (ex instanceof SystemException) {
      return systemExceptionHandler((SystemException) ex);
    } else {
      return otherExceptionHandler(ex);
    }
  }
}
