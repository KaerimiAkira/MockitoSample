package com.hyron.learn.controller;

import com.hyron.learn.ApiTestSupport;
import com.hyron.learn.client.MasterClient;
import com.hyron.learn.client.api.master.CustomerApi;
import com.hyron.learn.client.api.master.UserApi;
import com.hyron.learn.client.impl.MasterClientImpl;
import com.hyron.learn.model.database.UserEntity;
import com.hyron.learn.model.server.sample.CreateUserRequest;
import com.hyron.learn.model.server.sample.CreateUserResponse;
import com.hyron.learn.model.server.sample.GetUserResponse;
import com.hyron.learn.repository.UserRepository;
import com.hyron.learn.repository.impl.DefaultUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;

@DisplayName("SampleApi Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import({SampleApiTest.SampleApiMockConfiguration.class})
class SampleApiTest extends ApiTestSupport {
  @TestConfiguration(proxyBeanMethods = false)
  public static class SampleApiMockConfiguration {

    @Primary
    @Bean
    public UserRepository userRepository() {
      return Mockito.spy(DefaultUserRepository.class);
    }

    // NEWTONでは@Namedがプロキシ化されるが、なぜか再現できず、普通にモック化できる
    @Primary
    @Bean
    public MasterClient masterClient(CustomerApi customerApi, UserApi userApi) {
      return Mockito.spy(new MasterClientImpl(customerApi, userApi));
    }
  }

  @Autowired UserRepository userRepository;
  @Autowired MasterClient masterClient;

  @BeforeEach
  void beforeEach() {
    Mockito.clearInvocations(userRepository);
  }

  @Test
  @DisplayName("GetUser - normal")
  void test_get_user_01() throws Exception {
    // Mock
    Mockito.doReturn(
            new UserEntity()
                .setLoginId("000001")
                .setUserName("000001_name")
                .setMailAddress("dummy_mail@dummy.com")
                .setAddress("Shanghai"))
        .when(userRepository)
        .getUserByLoginId(Mockito.anyString());

    var params = new LinkedMultiValueMap<String, String>();
    params.add("loginId", "000001");

    var response = doGet("/sample/user/get", new HttpHeaders(), params, GetUserResponse.class);

    Assertions.assertThat(response).hasFieldOrPropertyWithValue("statusCode", HttpStatus.OK);
    Assertions.assertThat(response.getBody())
        .hasFieldOrPropertyWithValue("loginId", "000001")
        .hasFieldOrPropertyWithValue("userName", "000001_name");
  }

  @Test
  @DisplayName("CreateUser - Without mock.")
  void test_create_user_01() throws Exception {
    // Mock
    Mockito.doCallRealMethod().when(userRepository).createUser(Mockito.any());

    var request =
        new CreateUserRequest()
            .setLoginId("test_create_user_01")
            .setUserName("test_create_user_01_name");

    var response =
        doPost("/sample/user/create", new HttpHeaders(), request, CreateUserResponse.class);

    Assertions.assertThat(response).hasFieldOrPropertyWithValue("statusCode", HttpStatus.OK);
    Assertions.assertThat(response.getBody())
        .hasFieldOrPropertyWithValue("loginId", "test_create_user_01")
        .hasFieldOrPropertyWithValue("userName", "test_create_user_01_name");
  }

  @Test
  @DisplayName("CreateUser - Business exception.")
  void test_create_user_02() throws Exception {
    // Mock
    Mockito.doReturn(null).when(userRepository).createUser(Mockito.any());

    var request =
        new CreateUserRequest()
            .setLoginId("test_create_user_02")
            .setUserName("test_create_user_02_name");

    var response =
        doPost("/sample/user/create", new HttpHeaders(), request, CreateUserResponse.class);

    Assertions.assertThat(response)
        .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST);
  }

  @Test
  @DisplayName("CreateUser - Other exception")
  void test_create_user_03() throws Exception {
    // Mock
    Mockito.doThrow(new RuntimeException("mock exception."))
        .when(userRepository)
        .createUser(Mockito.any());

    var request =
        new CreateUserRequest()
            .setLoginId("test_create_user_03")
            .setUserName("test_create_user_03_name");

    var response =
        doPost("/sample/user/create", new HttpHeaders(), request, CreateUserResponse.class);

    Assertions.assertThat(response)
        .hasFieldOrPropertyWithValue("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  void test_get_user_call_server() {
    Mockito.doReturn(null).when(userRepository).getUserByLoginId(Mockito.anyString());
    Mockito.doReturn(null).when(masterClient).getUser(Mockito.anyString());

    var params = new LinkedMultiValueMap<String, String>();
    params.add("loginId", "000001");
    params.add("fromServer", "1");

    try {
      doGet("/sample/user/get_from_server", new HttpHeaders(), params, GetUserResponse.class);
    } catch (Exception e) {
      Assertions.fail("can't mock masterClient.");
    }
  }
}
