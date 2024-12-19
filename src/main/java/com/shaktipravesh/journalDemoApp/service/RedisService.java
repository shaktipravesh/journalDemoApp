package com.shaktipravesh.journalDemoApp.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }


    public <T> T get(String key, Class<T> entityClass) throws IllegalArgumentException {
        try {
            Object entityObject = redisTemplate.opsForValue().get(key);
            if (entityObject != null) {
                // Convert object to String and deserialize
                String json = entityObject.toString();
                return objectMapper.readValue(json, entityClass);
            } else {
                log.warn("No value found for key: {}", key);
            }
        } catch (JsonMappingException e) {
            log.error(e.getMessage());
            return null;
        } catch (RestClientException | IOException e) {
            log.error(e.getMessage());
            return null;
        }
        return null;
    }

    public void set(String key, Object value, Long ttl) throws RuntimeException {
        try {
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, json, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
