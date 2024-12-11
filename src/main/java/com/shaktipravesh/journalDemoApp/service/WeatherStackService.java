package com.shaktipravesh.journalDemoApp.service;

import com.shaktipravesh.journalDemoApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherStackService {
    private static final String APIKEY = "fafaa4355f160b9ea6f0a31aee2a0f36";
    private static final String API_URL = "https://api.weatherstack.com/current?";

    private final RestTemplate restTemplate;


    @Autowired
    public WeatherStackService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public WeatherResponse getWeather(String city) {
        String finalAPI = API_URL + "access_key=" + APIKEY + "&query=" + city;

        return restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class).getBody();
    }
}
