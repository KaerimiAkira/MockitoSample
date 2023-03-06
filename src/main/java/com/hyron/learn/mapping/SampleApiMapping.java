package com.hyron.learn.mapping;

import com.hyron.learn.model.database.OrderDetailEntity;
import com.hyron.learn.model.database.OrderEntity;
import com.hyron.learn.model.database.UserEntity;
import com.hyron.learn.model.server.sample.CreateUserRequest;
import com.hyron.learn.model.server.sample.CreateUserResponse;
import com.hyron.learn.model.server.sample.GetUserResponse;
import com.hyron.learn.model.service.OrderDetail;
import com.hyron.learn.model.service.OrderSlip;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SampleApiMapping {
  void mapping(@MappingTarget GetUserResponse target, UserEntity source);

  void mapping(@MappingTarget UserEntity target, CreateUserRequest source);

  void mapping(@MappingTarget CreateUserResponse target, UserEntity source);

  void mapping(@MappingTarget OrderSlip target, OrderEntity source);

  void mapping(@MappingTarget OrderDetail target, OrderDetailEntity source);
}
