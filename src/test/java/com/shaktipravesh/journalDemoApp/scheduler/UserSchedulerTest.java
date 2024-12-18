package com.shaktipravesh.journalDemoApp.scheduler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTest {

    @Autowired
    private UserScheduler userScheduler;

    @Test
    @Disabled
    public void testFetchUserNSendSAEmail() {
        Assertions.assertTrue(userScheduler.fetchUserNSendSAEmail());
    }

}
