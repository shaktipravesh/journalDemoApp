package com.shaktipravesh.journalDemoApp.service;

import com.shaktipravesh.journalDemoApp.entity.JournalMongoDBEntry;
import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalMongodbEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    UsersService usersService;

    public void saveEntry(JournalMongoDBEntry entry) {
        journalEntryRepository.save(entry);
    }

    @Transactional
    public void saveEntry(JournalMongoDBEntry entry, String userName) {
        try {
            User user = usersService.getUserByUserName(userName);
            if(user != null) {
                entry.setDate(LocalDateTime.now());
                JournalMongoDBEntry saved = journalEntryRepository.save(entry);
                user.getEntries().add(saved);
                usersService.saveEntry(user);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    @Transactional
    public void deleteEntryById(ObjectId id, String userName) {
        try {
            User user = usersService.getUserByUserName(userName);
            if(user.getEntries().removeIf(entry -> entry.getId().equals(id))) {
                usersService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
