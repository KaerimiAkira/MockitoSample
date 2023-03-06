package com.hyron.learn.repository.impl;

import com.hyron.learn.model.database.UserEntity;
import com.hyron.learn.repository.UserRepository;
import com.hyron.learn.utils.DateUtil;
import java.util.UUID;

public class DefaultUserRepository implements UserRepository {
  @Override
  public UserEntity getUserByLoginId(String userId) {
    // database connect
    // data query
    return new UserEntity()
        .setUserId(UUID.randomUUID().toString())
        .setLoginId("000001")
        .setUserName("SYSTEM")
        .setMailAddress("dummy@dummy.com")
        .setAddress("address: xxx")
        .setCreatedDateTime(DateUtil.currentSystemDateTime())
        .setUpdatedDateTime(DateUtil.currentSystemDateTime());
  }

  @Override
  public UserEntity createUser(UserEntity user) {
    // database connect
    // data insert
    return new UserEntity()
        .setUserId(UUID.randomUUID().toString())
        .setLoginId(user.getLoginId())
        .setUserName(user.getUserName())
        .setMailAddress(user.getMailAddress())
        .setAddress(user.getAddress())
        .setCreatedDateTime(DateUtil.currentSystemDateTime())
        .setUpdatedDateTime(DateUtil.currentSystemDateTime());
  }
}
