package com.hyron.learn.utils;

import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {

  public static ZonedDateTime currentSystemDateTime() {
    return ZonedDateTime.now();
  }

  public static boolean isOnBusiness(ZonedDateTime dateTime) {
    return dateTime.getHour() >= 8 && dateTime.getHour() < 20;
  }

  public static void sleep(long duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException ie) {
      log.info("sleep be interrupted.");
    }
  }
}
