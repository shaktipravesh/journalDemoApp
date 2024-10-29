package com.shaktipravesh.journalDemoApp.repository;

import com.shaktipravesh.journalDemoApp.entity.JournalMongoDBEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface JournalEntryRepository  extends MongoRepository<JournalMongoDBEntry, ObjectId> {
}
