package com.shaktipravesh.journalDemoApp.controller;

import com.shaktipravesh.journalDemoApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping("/entries")
    public List<JournalEntry> getAllJournalEntries() {
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("/entrypoint/{myId}")
    public JournalEntry getAllJournalEntryById(@PathVariable Long myId) {
        return journalEntries.get(myId);
    }

    @GetMapping("/entrypoint_param/")
    public JournalEntry getAllJournalEntryByParamId(@RequestParam Long myId ) {
        return journalEntries.get(myId);
    }

    @PostMapping("/entries")
    public boolean createEntry(@RequestBody JournalEntry entry) {
        journalEntries.put(entry.getId(), entry);
        return true;
    }

    @DeleteMapping("/entrypoint/{myId}")
    public boolean deleteEntrybyId(@PathVariable Long myId) {
        journalEntries.remove(myId);
        return true;
    }

    @PutMapping("/entrypoint/{myId}")
    public boolean updateEntryById(@RequestBody JournalEntry entry) {
        journalEntries.put(entry.getId(), entry);
        return true;
    }
}
