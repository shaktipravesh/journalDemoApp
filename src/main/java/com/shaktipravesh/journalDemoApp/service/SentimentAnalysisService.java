package com.shaktipravesh.journalDemoApp.service;

import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {

    public String getSentiment(String text) {
        if(text == null ||text.isEmpty()) {
            return "No Sentiment";
        }
        return "Sentiment Analysis";
    }

}
