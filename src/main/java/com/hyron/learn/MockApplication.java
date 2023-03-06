package com.hyron.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hyron.learn"})
public class MockApplication {

  public static void main(String[] args) {
    SpringApplication.run(MockApplication.class);
  }
}
