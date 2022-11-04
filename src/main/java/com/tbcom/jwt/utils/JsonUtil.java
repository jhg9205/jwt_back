package com.tbcom.jwt.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
