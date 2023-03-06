package com.hyron.learn.utils;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {
  public static final Pattern PATTERN_EMAIL = Pattern.compile("^[\\w-.]+@\\w+\\.[\\w.]+$");

  public static boolean isEmail(String input) {
    return StringUtils.isNotEmpty(input) && PATTERN_EMAIL.matcher(input).matches();
  }
}
