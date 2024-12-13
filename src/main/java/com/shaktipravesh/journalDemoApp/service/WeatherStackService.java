package com.shaktipravesh.journalDemoApp.service;

import com.shaktipravesh.journalDemoApp.api.response.WeatherResponse;
import com.shaktipravesh.journalDemoApp.cache.AppCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WeatherStackService {

    @Value("${weather.api.key}")
    public String apiKey;

    public String API_KEY;
    public String API_URL;

    final
    AppCache appCache;

    private static final String apiUrl = "https://api.weatherstack.com/current?";

    private final RestTemplate restTemplate;


    @Autowired
    public WeatherStackService(RestTemplateBuilder builder, AppCache appCache) {
        this.restTemplate = builder.build();
        this.appCache = appCache;
    }

    public WeatherResponse getWeather(String city) {
        API_KEY = appCache.appCache.get("weather_key");
        API_URL = appCache.appCache.get("weather_api");

        log.info("apiKey: {}", apiKey);
        log.info("apiUrl: {}", apiUrl);
        log.info("API_KEY: {}", API_KEY);
        log.info("API_URL: {}", API_URL);

        String finalAPI = API_URL + "access_key=" + API_KEY + "&query=" + city;

        return restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class).getBody();
    }
}
