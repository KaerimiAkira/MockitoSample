package com.hyron.learn.repository;

import com.hyron.learn.model.database.UserEntity;

public interface UserRepository {

  UserEntity getUserByLoginId(String userId);

  UserEntity createUser(UserEntity user);
}
