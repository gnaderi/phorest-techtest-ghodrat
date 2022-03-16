package com.naderi.phorest.salon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;

@Configuration
@EnableWebMvc
public class BeanConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean("yamlObjectMapper")
    public ObjectMapper yamlObjectMapper() {
        return new ObjectMapper(new YAMLFactory());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}