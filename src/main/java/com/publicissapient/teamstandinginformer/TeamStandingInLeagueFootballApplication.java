package com.publicissapient.teamstandinginformer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;

@SpringBootApplication
public class TeamStandingInLeagueFootballApplication {
    private static final Logger LOGGER = LogManager.getLogger(TeamStandingInLeagueFootballApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(TeamStandingInLeagueFootballApplication.class, args);
    }

    @Bean(name = "restClient")
    public RestTemplate getRestClient() {
        RestTemplate restClient = new RestTemplate(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        restClient.setInterceptors(Collections.singletonList((request, body, execution) -> {

            LOGGER.debug("Intercepting...");
            return execution.execute(request, body);
        }));

        return restClient;
    }
}
