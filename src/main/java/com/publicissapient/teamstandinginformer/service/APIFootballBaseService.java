package com.publicissapient.teamstandinginformer.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class APIFootballBaseService {
    private final String BASE_URL = "https://apifootball.com/api/?";
    private final String API_KEYS = "9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";

    private final RestTemplate restTemplate;

    public APIFootballBaseService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object callApi(final String path, final Class returnType) {
        return this.restTemplate.getForObject(buildUrl(path), returnType);
    }

    private String buildUrl(final String path) {
        return new StringBuilder().append(BASE_URL).append(path).append("&APIkey=").append(API_KEYS).toString();
    }

}
