package com.shaktipravesh.journalDemoApp.repository;

import com.shaktipravesh.journalDemoApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
}
