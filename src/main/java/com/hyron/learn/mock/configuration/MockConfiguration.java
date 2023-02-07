package com.hyron.learn.mock.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration(proxyBeanMethods = false)
public class MockConfiguration implements Jackson2ObjectMapperBuilderCustomizer {
  /**
   * Customize the JacksonObjectMapperBuilder.
   *
   * @param builder the JacksonObjectMapperBuilder to customize
   */
  @Override
  public void customize(Jackson2ObjectMapperBuilder builder) {
    builder.modulesToInstall(
        new SimpleModule() {
          @Override
          public void setupModule(SetupContext context) {
            super.setupModule(context);
          }
        },
        new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
    builder.serializationInclusion(JsonInclude.Include.NON_NULL);
    builder.featuresToEnable(MapperFeature.BLOCK_UNSAFE_POLYMORPHIC_BASE_TYPES);
    builder.featuresToEnable(SerializationFeature.INDENT_OUTPUT);
    builder.featuresToEnable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
    builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"));
    builder.serializers(
        new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")));
    builder.serializers(
        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
    builder.filters(new SimpleFilterProvider().setFailOnUnknownId(false));
    builder.postConfigurer(
        objectMapper -> {
          objectMapper
              .configOverride(BigDecimal.class)
              .setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.STRING));
          objectMapper
              .configOverride(BigInteger.class)
              .setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.STRING));
        });
  }
}
