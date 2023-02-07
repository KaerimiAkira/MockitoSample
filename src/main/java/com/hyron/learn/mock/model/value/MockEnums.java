package com.hyron.learn.mock.model.value;

public final class MockEnums {
  private MockEnums() {}

  public enum OrderStatus {
    WAIT_FOR_PAYMENT("1"),
    ORDERED("2"),
    COMPLETED("3"),
    CANCELED("4"),
    ;

    private final String value;

    OrderStatus(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }
}
