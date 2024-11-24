package com.shaktipravesh.journalDemoApp.service;

import com.shaktipravesh.journalDemoApp.entity.JournalMongoDBEntry;
import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void saveEntry(JournalMongoDBEntry entry, String userName) {
        User user = usersService.getUserByUserName(userName);
        entry.setDate(LocalDateTime.now());
        JournalMongoDBEntry saved = journalEntryRepository.save(entry);

        user.getEntries().add(saved);
        usersService.saveEntry(user);
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

    public void deleteEntryById(ObjectId id, String userName) {
        User user = usersService.getUserByUserName(userName);
        user.getEntries().removeIf(entry -> entry.getId().equals(id));
        usersService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }
}


//controller --> service --> repository