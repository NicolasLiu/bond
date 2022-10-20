package com.lkq.bond.config;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@Configuration
public class GsonConfig {
  @Bean
  public HttpMessageConverters customConverters() {
    Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
    converter.setGson(new GsonBuilder().setDateFormat("yyyy/MM/dd").serializeNulls().create());
    messageConverters.add(converter);
    return new HttpMessageConverters(true, messageConverters);
  }
}
