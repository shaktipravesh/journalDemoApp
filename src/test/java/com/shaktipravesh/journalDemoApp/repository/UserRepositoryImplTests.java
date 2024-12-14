package com.shaktipravesh.journalDemoApp.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    UserRepositoryImpl userRepository;

    @Test
    void testGetUserForSentimentAnalysis() {
        Assertions.assertNotNull(userRepository.getUserForSentimentAnalysis());
    }
}
