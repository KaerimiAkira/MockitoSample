package com.hyron.learn.mock.repository;

import com.hyron.learn.mock.model.database.UserEntity;

public interface UserRepository {

  UserEntity getUserByLoginId(String userId);

  UserEntity createUser(UserEntity user);
}
