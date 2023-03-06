package com.hyron.learn.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

@Slf4j
class BasePreAndSuffix {

  @BeforeAll
  static void beforeAllBase() {
    log.info("before all at superclass.");
  }

  @AfterAll
  static void AfterAllBase() {
    log.info("after all at superclass.");
  }

  @BeforeEach
  void beforeEachBase() {
    log.info("before each at superclass.");
  }

  @AfterEach
  void afterEachBase() {
    log.info("after each at superclass.");
  }
}
