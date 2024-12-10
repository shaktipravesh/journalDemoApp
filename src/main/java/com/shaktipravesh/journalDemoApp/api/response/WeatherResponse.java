package com.shaktipravesh.journalDemoApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class WeatherResponse {
    private Current current;

    @Getter
    @Setter
    @ToString
    private static class Current{
        @JsonProperty("observation_time")
        private String observationTime;
        private int temperature;
        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;
        private int feelsLike;
    }
}


