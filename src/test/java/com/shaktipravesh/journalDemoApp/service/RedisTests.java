package com.shaktipravesh.journalDemoApp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    @Disabled
    void testRedis() {
        redisTemplate.opsForValue().set("email", "shaktipravesh@yahoo.co.in");
        String email = (String) redisTemplate.opsForValue().get("email");
        Assertions.assertEquals("shaktipravesh@yahoo.co.in", email);
    }
}
