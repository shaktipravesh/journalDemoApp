package com.shaktipravesh.journalDemoApp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    EmailService emailService;


    @Test
    @Disabled // to avoid the email send while running application
    void testSendEmail() {
        Assertions.assertTrue(emailService.sendEmail("shaktipravesh@gmail.com", "testing email service", "Hello Sir ji aap kaise hai"));
    }
}
