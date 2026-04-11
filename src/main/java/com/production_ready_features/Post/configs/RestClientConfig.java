package com.production_ready_features.Post.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${employee.base.url}")
    private String BASE_URL;
    @Bean
    @Qualifier("employee")
    public RestClient getRestClientForEmployee() {
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError,(req,res)->
                {
                    System.out.println(new String(res.getBody().readAllBytes()));
                    throw new RuntimeException("server error occurred");
                })
                .build();

    }
}
