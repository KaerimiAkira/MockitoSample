package com.hyron.learn.service;

import com.hyron.learn.MockTestSupport;
import com.hyron.learn.exception.BusinessException;
import com.hyron.learn.mapping.SampleApiMapping;
import com.hyron.learn.mapping.SampleApiMappingImpl;
import com.hyron.learn.model.database.UserEntity;
import com.hyron.learn.model.server.sample.CreateUserRequest;
import com.hyron.learn.model.server.sample.CreateUserResponse;
import com.hyron.learn.repository.UserRepository;
import com.hyron.learn.repository.impl.DefaultUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;

@DisplayName("CreateUserService Test")
class CreateUserServiceTest {
  @Nested
  @DisplayName("Mock with constructor.")
  class WithConstructor extends MockTestSupport {
    private static final UserRepository userRepository =
        Mockito.mock(
            DefaultUserRepository.class,
            Mockito.withSettings().defaultAnswer(InvocationOnMock::callRealMethod));

    private static final SampleApiMapping sampleApiMapping = new SampleApiMappingImpl();
    private final CreateUserService target =
        new CreateUserService(userRepository, sampleApiMapping);

    @BeforeEach
    void beforeEach() {
      Mockito.reset(userRepository);
    }

    @Test
    @DisplayName("CreateUserService - create failed 1")
    void test_case001() {

      Mockito.doReturn(null).when(userRepository).createUser(Mockito.any());

      try {
        target.createUser(
            new CreateUserRequest()
                .setLoginId("000001")
                .setUserName("000001_name")
                .setMailAddress("000001@dummy.com")
                .setAddress("000001_address"));
      } catch (Exception ex) {
        if (ex instanceof BusinessException be) {
          Assertions.assertThat(be)
              .hasFieldOrPropertyWithValue("code", "BE0002")
              .hasFieldOrPropertyWithValue("message", "Can't create user with login id: 000001.");
          return;
        } else {
          Assertions.fail("unexpected exception: " + ex.getClass().getName());
        }
      }

      Assertions.fail("expect BusinessException");
    }

    @Test
    @DisplayName("CreateUserService - create failed 2")
    void test_case002() {
      Mockito.doReturn(
              new UserEntity()
                  .setLoginId("000001_db")
                  .setUserName("000001_name_db")
                  .setMailAddress("000001_db@dummy.com")
                  .setAddress("000001_address_db"))
          .when(userRepository)
          .createUser(Mockito.any());

      try {
        target.createUser(
            new CreateUserRequest()
                .setLoginId("000001")
                .setUserName("000001_name")
                .setMailAddress("000001@dummy.com")
                .setAddress("000001_address"));
      } catch (Exception ex) {
        if (ex instanceof BusinessException be) {
          Assertions.assertThat(be)
              .hasFieldOrPropertyWithValue("code", "BE0002")
              .hasFieldOrPropertyWithValue("message", "Can't create user with login id: 000001.");
          return;
        } else {
          Assertions.fail("unexpected exception: " + ex.getClass().getName());
        }
      }

      Assertions.fail("expect BusinessException");
    }

    @Test
    @DisplayName("CreateUserService - normal")
    void test_case003() {
      ArgumentCaptor<UserEntity> argument = ArgumentCaptor.forClass(UserEntity.class);
      Mockito.doReturn(
              new UserEntity()
                  .setUserId("000001_id")
                  .setLoginId("000001_db")
                  .setUserName("000001_name_db")
                  .setMailAddress("000001_db@dummy.com")
                  .setAddress("000001_address_db"))
          .when(userRepository)
          .createUser(argument.capture());

      CreateUserResponse response = null;
      try {
        response =
            target.createUser(
                new CreateUserRequest()
                    .setLoginId("000001")
                    .setUserName("000001_name")
                    .setMailAddress("000001@dummy.com")
                    .setAddress("000001_address"));
      } catch (Exception ex) {
        Assertions.fail("unexpected exception: " + ex.getClass().getName());
      }
      Mockito.verify(userRepository).createUser(argument.getValue());
      Assertions.assertThat(argument.getValue())
          .isNotNull()
          .hasFieldOrPropertyWithValue("loginId", "000001")
          .hasFieldOrPropertyWithValue("userName", "000001_name")
          .hasFieldOrPropertyWithValue("mailAddress", "000001@dummy.com")
          .hasFieldOrPropertyWithValue("address", "000001_address");
      Assertions.assertThat(response)
          .isNotNull()
          .hasFieldOrPropertyWithValue("loginId", "000001_db")
          .hasFieldOrPropertyWithValue("userName", "000001_name_db")
          .hasFieldOrPropertyWithValue("mailAddress", "000001_db@dummy.com")
          .hasFieldOrPropertyWithValue("address", "000001_address_db");
    }

    @Test
    @DisplayName("CreateUserService - without mock")
    void test_case004() {
      Mockito.doCallRealMethod().when(userRepository).createUser(Mockito.any());

      CreateUserResponse response = null;
      try {
        response =
            target.createUser(
                new CreateUserRequest()
                    .setLoginId("000001")
                    .setUserName("000001_name")
                    .setMailAddress("000001@dummy.com")
                    .setAddress("000001_address"));
      } catch (Exception ex) {
        Assertions.fail("unexpected exception: " + ex.getClass().getName());
      }

      Assertions.assertThat(response)
          .isNotNull()
          .hasFieldOrPropertyWithValue("loginId", "000001")
          .hasFieldOrPropertyWithValue("userName", "000001_name")
          .hasFieldOrPropertyWithValue("mailAddress", "000001@dummy.com")
          .hasFieldOrPropertyWithValue("address", "000001_address");
    }
  }

  @Nested
  @DisplayName("Mock with annotation.")
  class WithMockAnnotation extends MockTestSupport {
    @Spy
    final UserRepository userRepository =
        Mockito.mock(
            DefaultUserRepository.class,
            Mockito.withSettings().defaultAnswer(InvocationOnMock::callRealMethod));

    @Spy final SampleApiMapping sampleApiMapping = new SampleApiMappingImpl();
    @InjectMocks CreateUserService target;

    @BeforeEach
    void beforeEach() {
      Mockito.reset(userRepository);
    }

    @Test
    @DisplayName("CreateUserService - create failed 1")
    void test_case001() {

      Mockito.doReturn(null).when(userRepository).createUser(Mockito.any());

      try {
        target.createUser(
            new CreateUserRequest()
                .setLoginId("000001")
                .setUserName("000001_name")
                .setMailAddress("000001@dummy.com")
                .setAddress("000001_address"));
      } catch (Exception ex) {
        if (ex instanceof BusinessException be) {
          Assertions.assertThat(be)
              .hasFieldOrPropertyWithValue("code", "BE0002")
              .hasFieldOrPropertyWithValue("message", "Can't create user with login id: 000001.");
          return;
        } else {
          Assertions.fail("unexpected exception: " + ex.getClass().getName());
        }
      }

      Assertions.fail("expect BusinessException");
    }

    @Test
    @DisplayName("CreateUserService - create failed 2")
    void test_case002() {
      Mockito.doReturn(
              new UserEntity()
                  .setLoginId("000001_db")
                  .setUserName("000001_name_db")
                  .setMailAddress("000001_db@dummy.com")
                  .setAddress("000001_address_db"))
          .when(userRepository)
          .createUser(Mockito.any());

      try {
        target.createUser(
            new CreateUserRequest()
                .setLoginId("000001")
                .setUserName("000001_name")
                .setMailAddress("000001@dummy.com")
                .setAddress("000001_address"));
      } catch (Exception ex) {
        if (ex instanceof BusinessException be) {
          Assertions.assertThat(be)
              .hasFieldOrPropertyWithValue("code", "BE0002")
              .hasFieldOrPropertyWithValue("message", "Can't create user with login id: 000001.");
          return;
        } else {
          Assertions.fail("unexpected exception: " + ex.getClass().getName());
        }
      }

      Assertions.fail("expect BusinessException");
    }

    @Test
    @DisplayName("CreateUserService - normal")
    void test_case003() {
      ArgumentCaptor<UserEntity> argument = ArgumentCaptor.forClass(UserEntity.class);
      Mockito.doReturn(
              new UserEntity()
                  .setUserId("000001_id")
                  .setLoginId("000001_db")
                  .setUserName("000001_name_db")
                  .setMailAddress("000001_db@dummy.com")
                  .setAddress("000001_address_db"))
          .when(userRepository)
          .createUser(argument.capture());

      CreateUserResponse response = null;
      try {
        response =
            target.createUser(
                new CreateUserRequest()
                    .setLoginId("000001")
                    .setUserName("000001_name")
                    .setMailAddress("000001@dummy.com")
                    .setAddress("000001_address"));
      } catch (Exception ex) {
        Assertions.fail("unexpected exception: " + ex.getClass().getName());
      }
      Mockito.verify(userRepository).createUser(argument.getValue());
      Assertions.assertThat(argument.getValue())
          .isNotNull()
          .hasFieldOrPropertyWithValue("loginId", "000001")
          .hasFieldOrPropertyWithValue("userName", "000001_name")
          .hasFieldOrPropertyWithValue("mailAddress", "000001@dummy.com")
          .hasFieldOrPropertyWithValue("address", "000001_address");
      Assertions.assertThat(response)
          .isNotNull()
          .hasFieldOrPropertyWithValue("loginId", "000001_db")
          .hasFieldOrPropertyWithValue("userName", "000001_name_db")
          .hasFieldOrPropertyWithValue("mailAddress", "000001_db@dummy.com")
          .hasFieldOrPropertyWithValue("address", "000001_address_db");
    }

    @Test
    @DisplayName("CreateUserService - without mock")
    void test_case004() {
      Mockito.doCallRealMethod().when(userRepository).createUser(Mockito.any());

      CreateUserResponse response = null;
      try {
        response =
            target.createUser(
                new CreateUserRequest()
                    .setLoginId("000001")
                    .setUserName("000001_name")
                    .setMailAddress("000001@dummy.com")
                    .setAddress("000001_address"));
      } catch (Exception ex) {
        Assertions.fail("unexpected exception: " + ex.getClass().getName());
      }

      Assertions.assertThat(response)
          .isNotNull()
          .hasFieldOrPropertyWithValue("loginId", "000001")
          .hasFieldOrPropertyWithValue("userName", "000001_name")
          .hasFieldOrPropertyWithValue("mailAddress", "000001@dummy.com")
          .hasFieldOrPropertyWithValue("address", "000001_address");
    }
  }
}
