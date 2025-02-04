package com.integration.development.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfiguration {

    private final String githubToken;

    public RestTemplateConfiguration(@Value("${version.control.github.token}") String githubToken) {
        this.githubToken = githubToken;
    }

    @Bean(name = "githubTemplate")
    public RestTemplate restTemplate() {
        var template = new RestTemplate();

        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().add("Accept", "application/vnd.github.v3+json");
            request.getHeaders().add("Authorization", "token " + this.githubToken);
            request.getHeaders().add("X-Github-Api-Version", "2022-11-28");
            return execution.execute(request, body);
        };

        template.setInterceptors(List.of(interceptor));
        return template;
    }

}
