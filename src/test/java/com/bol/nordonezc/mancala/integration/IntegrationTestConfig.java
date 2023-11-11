package com.bol.nordonezc.mancala.integration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication(scanBasePackages = "com.bol.nordonezc.mancala")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class IntegrationTestConfig {
    @Bean
    public ObjectMapper ObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}