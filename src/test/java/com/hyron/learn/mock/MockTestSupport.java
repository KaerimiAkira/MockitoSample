package com.hyron.learn.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public abstract class MockTestSupport {
  private AutoCloseable closeable;

  @BeforeEach
  void beforeEachBase() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void afterEachBase() throws Exception {
    closeable.close();
  }
}
