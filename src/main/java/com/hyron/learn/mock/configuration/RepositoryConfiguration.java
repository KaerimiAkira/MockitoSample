package com.hyron.learn.mock.configuration;

import com.hyron.learn.mock.repository.OrderRepository;
import com.hyron.learn.mock.repository.UserRepository;
import com.hyron.learn.mock.repository.impl.DefaultOrderRepository;
import com.hyron.learn.mock.repository.impl.DefaultUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class RepositoryConfiguration {

  @Bean
  public UserRepository userRepository() {
    return new DefaultUserRepository();
  }

  @Bean
  public OrderRepository orderRepository() {
    return new DefaultOrderRepository();
  }
}
