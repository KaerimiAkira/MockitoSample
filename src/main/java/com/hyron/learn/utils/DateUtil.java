package com.hyron.learn.utils;

import java.time.ZonedDateTime;

public class DateUtil {

  public static ZonedDateTime currentSystemDateTime() {
    return ZonedDateTime.now();
  }

  public static boolean isOnBusiness(ZonedDateTime dateTime) {
    return dateTime.getHour() >= 8 && dateTime.getHour() < 20;
  }
}
