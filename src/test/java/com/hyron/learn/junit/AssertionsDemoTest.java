package com.hyron.learn.junit;

import com.hyron.learn.utils.DateUtil;
import com.hyron.learn.utils.StringUtil;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Slf4j
public class AssertionsDemoTest {

  @ParameterizedTest
  @ValueSource(strings = {"xxx@xxx.com", "xxx@xxx"})
  void test_assert_with_message(String input) {
    // Assertions.assertTrue(StringUtil.isEmail(input), input + "is invalid mail.");
    // Assertions.assertTrue(StringUtil.isEmail(input), () -> input + " is invalid mail.");
    Assertions.assertTrue(() -> StringUtil.isEmail(input), () -> input + " is invalid mail.");
  }

  @ParameterizedTest
  @ValueSource(strings = {"123.x"})
  void test_assert_exception(String input) {
    var ex =
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> StringUtil.toNumeric(input),
            "When NumberFormatException be thrown, assertThrows will success if IllegalArgumentException be specified.");
    log.info("exception type: {}", ex.getClass().getName());
  }

  @ParameterizedTest
  @ValueSource(strings = {"123.x"})
  void test_assert_exception_with_fail(String input) {
    try {
      StringUtil.toNumeric(input);
      // Assertions.fail("expected NumberFormatException when " + input + " try to convert to
      // number.");
      Assertions.fail(
          () -> "expected NumberFormatException when " + input + " try to convert to number.");
    } catch (Exception e) {
      Assertions.assertInstanceOf(IllegalArgumentException.class, e);
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {"123.x"})
  void test_assert_exception_exactly_failed(String input) {
    Assertions.assertThrowsExactly(
        IllegalArgumentException.class,
        () -> StringUtil.toNumeric(input),
        "When NumberFormatException be thrown, assertThrowsExactly will fail if IllegalArgumentException be specified.");
  }

  @ParameterizedTest
  @ValueSource(strings = {"123.x"})
  void test_assert_exception_exactly_success(String input) {
    var ex =
        Assertions.assertThrowsExactly(
            NumberFormatException.class,
            () -> StringUtil.toNumeric(input),
            "When NumberFormatException be thrown, assertThrowsExactly only success if NumberFormatException be specified.");
    log.info("exception type:{}", ex.getClass().getName());
  }

  @ParameterizedTest
  @ValueSource(strings = {"123", "123.12"})
  void test_assert_not_throw(String input) {
    var result = Assertions.assertDoesNotThrow(() -> StringUtil.toNumeric(input));
    log.info("result: {}", result);
  }

  @ParameterizedTest
  @MethodSource
  void test_assert_all(ArgumentsAccessor accessor) {
    Assertions.assertAll(
        "assert all:",
        () -> Assertions.assertEquals("str_1", accessor.getString(0)),
        () ->
            Assertions.assertEquals(
                0, accessor.getInteger(1), "display when accessor[1] assert failed."),
        () ->
            Assertions.assertTrue(
                accessor.getBoolean(2), "display when accessor[2] assert failed."));
  }

  static Stream<Arguments> test_assert_all() {
    return Stream.of(
        Arguments.arguments("str_1", 0, true),
        Arguments.arguments("str_1", 0, false),
        Arguments.of("str_1", 1, false));
  }

  @ParameterizedTest
  @MethodSource
  void test_assert_arrays(Object[] input, Object[] expected) {
    Assertions.assertArrayEquals(expected, input, "failed message");
  }

  static Stream<Arguments> test_assert_arrays() {
    return Stream.of(
        Arguments.arguments(
            new Object[] {"line1", "line2", "line3"}, new Object[] {"line1", "line2", "line3"}),
        Arguments.arguments(
            new Object[] {"line1", "line2"}, new Object[] {"line1", "line2", "line3"}),
        Arguments.arguments(
            new Object[] {"line1", "line2", "line3"}, new Object[] {"line1", "line2", null}),
        Arguments.arguments(
            new Object[] {"line1", "line2", "line3"}, new Object[] {"line1", "line1", "line2"}));
  }

  @ParameterizedTest
  @MethodSource
  void test_assert_equals_or_same_1(TestBeanWithoutEquals input, TestBeanWithoutEquals expected) {
    Assertions.assertAll(
        "assert all:",
        () -> Assertions.assertEquals(expected, input, "failed at equals"),
        () -> Assertions.assertSame(expected, input, "failed at same"));
  }

  static Stream<Arguments> test_assert_equals_or_same_1() {
    var bean1 = new TestBeanWithoutEquals().setId("id_1").setName("name_1").setAddress("address_1");
    var bean2 = new TestBeanWithoutEquals().setId("id_1").setName("name_1").setAddress("address_1");
    var bean3 = bean1;
    return Stream.of(Arguments.of(bean1, bean2), Arguments.of(bean1, bean3));
  }

  @ParameterizedTest
  @MethodSource
  void test_assert_equals_or_same_2(TestBeanWithEquals input, TestBeanWithEquals expected) {
    Assertions.assertAll(
        "assert all:",
        () -> Assertions.assertEquals(expected, input, "failed at equals"),
        () -> Assertions.assertSame(expected, input, "failed at same"));
  }

  static Stream<Arguments> test_assert_equals_or_same_2() {
    var bean1 = new TestBeanWithEquals().setId("id_1").setName("name_1").setAddress("address_1");
    var bean2 = new TestBeanWithEquals().setId("id_1").setName("name_1").setAddress("address_1");
    var bean3 = bean1;
    return Stream.of(Arguments.of(bean1, bean2), Arguments.of(bean1, bean3));
  }

  @ParameterizedTest
  @MethodSource
  void test_assert_iterable_equal(Iterable<Integer> input, Iterable<Integer> expected) {
    Assertions.assertIterableEquals(expected, input, "iterable equals failed.");
  }

  static Stream<Arguments> test_assert_iterable_equal() {
    return Stream.of(
        Arguments.of(Arrays.asList(10, 20, 30), Arrays.asList(10, 20, 30)),
        Arguments.of(Arrays.asList(10, 20, 30), Arrays.asList(10, 10, 20)),
        Arguments.of(Arrays.asList(10, 20), Arrays.asList(10, 20, 30)));
  }

  @ParameterizedTest
  @MethodSource
  void test_assert_line_match(List<String> input, List<String> expected) {
    Assertions.assertLinesMatch(expected, input);
  }

  static Stream<Arguments> test_assert_line_match() {
    var patternList1 = Arrays.asList("\\w+", ".*", "exactlyMatch");
    return Stream.of(
        Arguments.of(Arrays.asList("abc", "123", "exactlyMatch"), patternList1),
        Arguments.of(Arrays.asList("123", "abc", "match"), patternList1),
        Arguments.of(
            Arrays.asList("abc", "a1", "a2", "a3", "123"),
            Arrays.asList("\\w+", ">>>>", "\\d+")),
        Arguments.of(
            Arrays.asList("123", "a1", "a2", "\t\n", "abc"),
            Arrays.asList("\\d+", ">>2>>", "\\W+", "\\w+")));
  }

  @Test
  void test_assert_timeout() {
    Assertions.assertTimeout(
        Duration.of(10, ChronoUnit.SECONDS), () -> DateUtil.sleep(1000), "not be displayed.");
    Assertions.assertTimeout(
        Duration.of(2, ChronoUnit.SECONDS), () -> DateUtil.sleep(2500), "execute over time.");
    var result =
        Assertions.assertTimeout(
            Duration.of(1, ChronoUnit.SECONDS), DateUtil::currentSystemDateTime);
    // do something with result
  }

  @Test
  void test_assert_timeout_preemptively() throws Exception {
    Assertions.assertTimeoutPreemptively(
        Duration.of(10, ChronoUnit.SECONDS), () -> DateUtil.sleep(1000), "not be displayed.");
    Assertions.assertTimeoutPreemptively(
        Duration.of(2, ChronoUnit.SECONDS), () -> DateUtil.sleep(2500), "execute over time.");
    Assertions.assertTimeoutPreemptively(
        Duration.of(1, ChronoUnit.SECONDS),
        DateUtil::currentSystemDateTime,
        () -> "display when failed.",
        (timeout, messageSupplier, cause) -> new TimeoutException(""));
  }

  @Getter
  @Setter
  @Accessors(chain = true)
  private static class TestBeanWithoutEquals {

    private String id;
    private String name;
    private String address;
  }

  @Getter
  @Setter
  @Accessors(chain = true)
  private static class TestBeanWithEquals {
    private String id;
    private String name;
    private String address;

    @Override
    public boolean equals(Object input) {
      if (input == null) {
        return false;
      }
      if (!(input instanceof TestBeanWithEquals another)) {
        return false;
      }

      return Objects.equals(id, another.id)
          && Objects.equals(name, another.name)
          && Objects.equals(address, another.address);
    }
  }
}
