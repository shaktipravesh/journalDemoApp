package com.shaktipravesh.journalDemoApp.repository;

import com.shaktipravesh.journalDemoApp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserRepositoryImpl {

    private final MongoTemplate mongoTemplate;

    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<User> getUserForSentimentAnalysis() {
        List<User> users = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("email").regex("[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
            query.addCriteria(Criteria.where("sentimentAnalysis").exists(true));
            users = mongoTemplate.find(query, User.class);
            log.info("users size: " + users.size());
            users = mongoTemplate.find(query, User.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return users;
    }
}
