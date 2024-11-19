package com.shaktipravesh.journalDemoApp.controller;

import com.shaktipravesh.journalDemoApp.entity.JournalMongoDBEntry;
import com.shaktipravesh.journalDemoApp.service.JournalMongodbEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal_mongodb")
public class JournalEntryMongoDBController {

    @Autowired
    private JournalMongodbEntryService journalMongodbEntryService;

    private final Map<String, JournalMongoDBEntry> journalEntries = new HashMap<String, JournalMongoDBEntry>();

    @GetMapping
    public List<JournalMongoDBEntry> getAll() {
        return journalMongodbEntryService.getAllEntries();
    }

    @PostMapping
    public JournalMongoDBEntry createMongoDBEntry(@RequestBody JournalMongoDBEntry entry) {
        entry.setDate(LocalDateTime.now());
        journalMongodbEntryService.saveEntry(entry);
        return entry;
    }

    @GetMapping("id/{myId}")
    public JournalMongoDBEntry getMongoDBEntryById(@PathVariable ObjectId myId) {
        return journalMongodbEntryService.getEntryById(myId).orElse(null);
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteMongoDBEntry(@PathVariable ObjectId myId) {
        journalMongodbEntryService.deleteEntryById(myId);
        return true;
    }

    @PutMapping({"id/{myId}"})
    public JournalMongoDBEntry updateMongoDBEntry(@PathVariable ObjectId myId, @RequestBody JournalMongoDBEntry newEntry) {
        JournalMongoDBEntry oldEntry = journalMongodbEntryService.getEntryById(myId).orElse(null);
        if(oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
        }
        journalMongodbEntryService.saveEntry(oldEntry);
        return newEntry;
    }

    @GetMapping("/entries")
    public List<JournalMongoDBEntry> getAllJournalEntries() {
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("/entrypoint/{myId}")
    public JournalMongoDBEntry getAllJournalEntryById(@PathVariable String myId) {
        return journalEntries.get(myId);
    }

    @PostMapping("/entries")
    public boolean createEntry(@RequestBody JournalMongoDBEntry entry) {
        journalEntries.put(entry.getId(), entry);
        return true;
    }

    @DeleteMapping("/entrypoint/{myId}")
    public boolean deleteEntrybyId(@PathVariable String myId) {
        journalEntries.remove(myId);
        return true;
    }

    @PutMapping("/entrypoint/{myId}")
    public boolean updateEntryById(@RequestBody JournalMongoDBEntry entry) {
        journalEntries.put(entry.getId(), entry);
        return true;
    }
}
