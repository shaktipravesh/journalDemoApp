package com.shaktipravesh.journalDemoApp.service;

import com.shaktipravesh.journalDemoApp.entity.JournalMongoDBEntry;
import com.shaktipravesh.journalDemoApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalMongodbEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalMongoDBEntry entry) {
        journalEntryRepository.save(entry);
    }

    public List<JournalMongoDBEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalMongoDBEntry> getEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteEntryById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }
}


//controller --> service --> repository