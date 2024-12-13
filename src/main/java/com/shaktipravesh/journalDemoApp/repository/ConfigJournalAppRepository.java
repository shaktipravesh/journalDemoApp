package com.shaktipravesh.journalDemoApp.repository;

import com.shaktipravesh.journalDemoApp.entity.ConfigJournalAppEntity;
import com.shaktipravesh.journalDemoApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
