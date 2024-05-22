package org.example.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MainServiceConfig {

    @Bean
    public RestTemplate mainServiceRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}