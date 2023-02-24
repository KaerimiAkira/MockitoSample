package com.hyron.learn.mock.util;

import com.hyron.learn.mock.utils.StringUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@Slf4j
class IsEmailTest {
  @Test
  void test_isEmail() {
    String input = "dummy@dumm.com";
    Assertions.assertTrue(StringUtil.isEmail(input));

    input = "12345@com";
    Assertions.assertFalse(StringUtil.isEmail(input));

    input = "xxx@hyron.com.cn";
    Assertions.assertTrue(StringUtil.isEmail(input));

    input = "xxx-xxx_xxx@hyron.com.cn";
    Assertions.assertTrue(StringUtil.isEmail(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {"dummy@dumm.com", "xxx@hyron.com.cn", "xxx-xxx_xxx@hyron.com.cn"})
  void test_isEmail_with_values(String input) {
    Assertions.assertTrue(StringUtil.isEmail(input));
  }

  @ParameterizedTest
  @MethodSource
  void test_isEmail_with_string_stream(String input) {
    Assertions.assertTrue(StringUtil.isEmail(input));
  }

  static Stream<String> test_isEmail_with_string_stream() {
    return Stream.of("dummy@dumm.com", "xxx@hyron.com.cn", "xxx-xxx_xxx@hyron.com.cn");
  }

  @ParameterizedTest
  @MethodSource
  void test_isEmail_with_default_method_provider(String input, Boolean expected) {
    Assertions.assertEquals(expected, StringUtil.isEmail(input));
  }

  static Stream<Arguments> test_isEmail_with_default_method_provider() {
    return Stream.of(
        Arguments.arguments("dummy@dumm.com", Boolean.TRUE),
        Arguments.arguments("12345@com", Boolean.FALSE),
        Arguments.arguments("xxx@hyron.com.cn", Boolean.TRUE),
        Arguments.arguments("xxx-xxx_xxx@hyron.com.cn", Boolean.TRUE));
  }

  @ParameterizedTest
  @MethodSource("specifiedMethodProvider")
  void test_isEmail_with_specified_method_provider(String input, Boolean expected) {
    Assertions.assertEquals(expected, StringUtil.isEmail(input));
  }

  @ParameterizedTest
  @MethodSource("specifiedMethodProvider")
  void test_isEmail_with_accessor(ArgumentsAccessor accessor) {
    Assertions.assertEquals(accessor.getBoolean(1), StringUtil.isEmail(accessor.getString(0)));
  }

  static Stream<Arguments> specifiedMethodProvider() {
    return test_isEmail_with_default_method_provider();
  }

  @ParameterizedTest
  @MethodSource("com.hyron.learn.mock.util.IsEmailTest$NestedProvider#nestedProvider")
  void test_isEmail_with_nested_provider(String input, Boolean expected) {
    Assertions.assertEquals(expected, StringUtil.isEmail(input));
  }

  static class NestedProvider {
    static Stream<Arguments> nestedProvider() {
      return IsEmailTest.test_isEmail_with_default_method_provider();
    }
  }

  @ParameterizedTest
  @MethodSource("com.hyron.learn.mock.util.ExternalProvider#externalProvider")
  void test_isEmail_with_external_provider(String input, Boolean expected) {
    Assertions.assertEquals(expected, StringUtil.isEmail(input));
  }

  @ParameterizedTest(
      name =
          "["
              + ParameterizedTest.INDEX_PLACEHOLDER
              + "] "
              + ParameterizedTest.ARGUMENTS_PLACEHOLDER)
  @CsvSource(
      useHeadersInDisplayName = true,
      textBlock =
          """
      CSV_INPUT,CSV_EXPECTED
      dummy@dumm.com,TRUE
      12345@com,FALSE
      xxx@hyron.com.cn,TRUE
      xxx-xxx_xxx.xxx@hyron.com.cn,TRUE
      """)
  void test_isEmail_with_csv_format(String input, Boolean expected) {
    Assertions.assertEquals(expected, StringUtil.isEmail(input));
  }

  @ParameterizedTest
  @CsvSource(
      useHeadersInDisplayName = true,
      value = {
        "CSV_INPUT,CSV_EXPECTED",
        "dummy@dumm.com,TRUE",
        "12345@com,FALSE",
        "xxx@hyron.com.cn,TRUE",
        "xxx-xxx_xxx.xxx@hyron.com.cn,TRUE"
      })
  void test_isEmail_with_csv_format_use_value(String input, Boolean expected) {
    Assertions.assertEquals(expected, StringUtil.isEmail(input));
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = '\t',
      quoteCharacter = '\"',
      nullValues = {"NVL", "<<null>>"},
      emptyValue = "<<empty>>",
      textBlock =
          """
      #INPUT\tEXPECTED
      dummy@dumm.com\tTRUE
      12345@com\tFALSE
      xxx@hyron.com.cn\tTRUE
      xxx-xxx_xxx.xxx@hyron.com.cn\tTRUE
      \tFALSE
      NVL\tFALSE
      <<null>>\tFALSE
      ""\tFALSE
      """)
  void test_isEmail_with_csv_delimiter(String input, Boolean expected) {
    Assertions.assertEquals(expected, StringUtil.isEmail(input));
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = "isEmail_external.csv",
      lineSeparator = "\r\n",
      quoteCharacter = '\'',
      delimiter = '\t',
      numLinesToSkip = 1,
      nullValues = {"NVL", "<<null>>"})
  void test_isEmail_with_csv_file(String input, Boolean expected) {
    Assertions.assertEquals(expected, StringUtil.isEmail(input));
  }

  @Nested
  @ExtendWith(IsEmailWithNgResolver.NgResolver.class)
  class IsEmailWithNgResolver {
    @RepeatedTest(10)
    void test_isEmail_all_ng(String input) {
      Assertions.assertFalse(StringUtil.isEmail(input));
    }

    static class NgResolver implements ParameterResolver {
      private static final List<String> NG_MAIL_LIST =
          Arrays.asList("dummy", "12345@com", "dummy@dummy@com.cn", "123+abc@dummy.com.cn", "");

      @Override
      public boolean supportsParameter(
          ParameterContext parameterContext, ExtensionContext extensionContext)
          throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == String.class;
      }

      @Override
      public Object resolveParameter(
          ParameterContext parameterContext, ExtensionContext extensionContext)
          throws ParameterResolutionException {
        return NG_MAIL_LIST.get(new Random().nextInt(NG_MAIL_LIST.size() - 1));
      }
    }
  }

  @Nested
  class IsEmailWithOkResolver {
    @RegisterExtension static final OkResolver okResolver = new OkResolver();

    @RepeatedTest(10)
    void test_isEmail_all_ok(String input) {
      Assertions.assertTrue(StringUtil.isEmail(input));
    }

    static class OkResolver implements ParameterResolver {
      private static final List<String> OK_MAIL_LIST =
          Arrays.asList(
              "dummy@dummy.com",
              "xxx@hyron.com.cn",
              "xxx-xxx_xxx.xxx@hyron.com.cn",
              "gu-xxxx-xm@nri.co.jp",
              "10086@qq.com");

      @Override
      public boolean supportsParameter(
          ParameterContext parameterContext, ExtensionContext extensionContext)
          throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == String.class;
      }

      @Override
      public Object resolveParameter(
          ParameterContext parameterContext, ExtensionContext extensionContext)
          throws ParameterResolutionException {
        return OK_MAIL_LIST.get(new Random().nextInt(OK_MAIL_LIST.size() - 1));
      }
    }
  }
}

class ExternalProvider {
  static Stream<Arguments> externalProvider() {
    return IsEmailTest.test_isEmail_with_default_method_provider();
  }
}
