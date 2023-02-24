package com.hyron.learn.mock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;

public abstract class ApiTestSupport {
  @Autowired protected ObjectMapper objectMapper;
  @Autowired protected MockMvc mockMvc;

  private AutoCloseable closeable;

  @BeforeEach
  void beforeEachBase() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void afterEachBase() throws Exception {
    closeable.close();
  }

  protected <T> ResponseEntity<T> doGet(
      String apiUrl, HttpHeaders headers, MultiValueMap<String, String> params, Class<T> resType)
      throws Exception {
    MockHttpServletResponse response;
    ResponseEntity<T> resEntity;

    response =
        mockMvc
            .perform(
                get(apiUrl)
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                    .params(params))
            .andDo(
                (result ->
                    result.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name())))
            .andDo(print())
            .andReturn()
            .getResponse();
    resEntity =
        new ResponseEntity<>(
            objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), resType),
            headers,
            Objects.requireNonNull(HttpStatus.resolve(response.getStatus())));

    return resEntity;
  }

  protected <T> ResponseEntity<T> doPost(
      String apiUrl, HttpHeaders headers, Object request, Class<T> resType) throws Exception {
    MockHttpServletResponse response;
    ResponseEntity<T> resEntity;

    response =
        mockMvc
            .perform(
                post(apiUrl)
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andDo(
                (result ->
                    result.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name())))
            .andDo(print())
            .andReturn()
            .getResponse();
    resEntity =
        new ResponseEntity<>(
            objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), resType),
            headers,
            Objects.requireNonNull(HttpStatus.resolve(response.getStatus())));

    return resEntity;
  }
}
