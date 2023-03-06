package com.hyron.learn.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class PreAndSuffixTest extends BasePreAndSuffix {
  @BeforeAll
  static void beforeAll() {
    log.info("before all at test class.");
  }

  @AfterAll
  static void afterAll() {
    log.info("after all at test class.");
  }

  @BeforeEach
  void beforeEach() {
    log.info("before each at test class.");
  }

  @AfterEach
  void afterEach() {
    log.info("after each at test class.");
  }

  @Test
  void test_prefix_and_suffix_case01() {
    log.info("at test method test_prefix_and_suffix_case01.");
  }

  @Test
  void test_prefix_and_suffix_case02() {
    log.info("at test method test_prefix_and_suffix_case02.");
  }
}
