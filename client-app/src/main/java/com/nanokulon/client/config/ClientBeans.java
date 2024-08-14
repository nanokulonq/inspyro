package com.nanokulon.client.config;

import com.nanokulon.client.client.RestClientIdeasRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientIdeasRestClient ideasRestClient(
            @Value("${inspyro.services.ideas.uri:http://localhost:8081}") String ideasBaseUri,
            @Value("${inspyro.services.ideas.username:}") String ideasUsername,
            @Value("${inspyro.services.ideas.password:}") String ideasPassword) {
        return new RestClientIdeasRestClient(RestClient.builder()
                .baseUrl(ideasBaseUri)
                .requestInterceptor(
                        new BasicAuthenticationInterceptor(ideasUsername, ideasPassword))
                .build());
    }
}
