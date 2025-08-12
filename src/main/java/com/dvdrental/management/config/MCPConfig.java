package com.dvdrental.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import java.time.Duration;

/**
 * Configuration class for MCP (Model Context Protocol) integration.
 * Sets up RestTemplate and other beans needed for MCP server communication.
 */
@Configuration
public class MCPConfig {
    
    /**
     * Configure RestTemplate for MCP server communication
     * with appropriate timeouts and message converters
     */
    @Bean
    public RestTemplate mcpRestTemplate(RestTemplateBuilder builder) {
        return builder
            .setConnectTimeout(Duration.ofSeconds(30))
            .setReadTimeout(Duration.ofSeconds(60))
            .requestFactory(() -> {
                SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
                factory.setConnectTimeout(30000); // 30 seconds
                factory.setReadTimeout(60000);    // 60 seconds
                return factory;
            })
            .additionalMessageConverters(
                new StringHttpMessageConverter(),
                new ByteArrayHttpMessageConverter()
            )
            .build();
    }
    
    /**
     * Configure Jackson message converter for JSON handling
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setPrettyPrint(true);
        return converter;
    }
}
