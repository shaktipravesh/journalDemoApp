package com.shaktipravesh.journalDemoApp.service;

import com.shaktipravesh.journalDemoApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherStackService {

    @Value("${weather.api.key}")
    public String apiKey;

    private static final String API_URL = "https://api.weatherstack.com/current?";

    private final RestTemplate restTemplate;


    @Autowired
    public WeatherStackService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public WeatherResponse getWeather(String city) {
        String finalAPI = API_URL + "access_key=" + apiKey + "&query=" + city;

        return restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class).getBody();
    }
}
