package com.hyron.learn.configuration;

import com.hyron.learn.repository.OrderRepository;
import com.hyron.learn.repository.UserRepository;
import com.hyron.learn.repository.impl.DefaultOrderRepository;
import com.hyron.learn.repository.impl.DefaultUserRepository;
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
