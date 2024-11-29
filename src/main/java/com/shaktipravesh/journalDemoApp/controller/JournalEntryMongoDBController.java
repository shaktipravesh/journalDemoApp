package com.shaktipravesh.journalDemoApp.controller;

import com.shaktipravesh.journalDemoApp.entity.JournalMongoDBEntry;
import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.service.JournalMongodbEntryService;
import com.shaktipravesh.journalDemoApp.service.UsersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/journal_mongodb")
public class JournalEntryMongoDBController {

    @Autowired
    private JournalMongodbEntryService journalMongodbEntryService;

    @Autowired
    private UsersService usersService;

    private final Map<ObjectId, JournalMongoDBEntry> journalEntries = new HashMap<>();

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            System.out.println("getAll called");
            List<JournalMongoDBEntry> all = journalMongodbEntryService.getAllEntries();
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authUserName = authentication.getName();

        try {
            User user = usersService.getUserByUserName(authUserName);
            List<JournalMongoDBEntry> all = user.getEntries();//journalMongodbEntryService.getAllEntries();
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<JournalMongoDBEntry> createMongoDBEntry(@RequestBody JournalMongoDBEntry entry) {
        System.out.println("createMongoDBEntry called");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authUserName = authentication.getName();
        try {
            journalMongodbEntryService.saveEntry(entry, authUserName);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getMongoDBEntryById(@PathVariable ObjectId myId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String authUserName = authentication.getName();
            User user = usersService.getUserByUserName(authUserName);
            List<JournalMongoDBEntry> allEntries = user.getEntries().stream().filter(x -> x.getId().equals(myId)).toList();
            if(!allEntries.isEmpty()) {
                Optional<JournalMongoDBEntry> entry = journalMongodbEntryService.getEntryById(myId);
                return entry.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
            } else {
                return new ResponseEntity<>("Not Authorised for this id", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteMongoDBEntry(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authUserName = authentication.getName();
        try {
            journalMongodbEntryService.deleteEntryById(myId, authUserName);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping({"id/{myId}"})
    public ResponseEntity<?> updateMongoDBUserNameEntry(@PathVariable ObjectId myId, @RequestBody JournalMongoDBEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authUserName = authentication.getName();
        User user = usersService.getUserByUserName(authUserName);
        List<JournalMongoDBEntry> entries = user.getEntries();
        for (JournalMongoDBEntry entry : entries) {
            if(entry.getId().equals(myId)) {
                return getResponseEntity(myId, newEntry);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    private ResponseEntity<?> getResponseEntity(@PathVariable ObjectId myId, @RequestBody JournalMongoDBEntry newEntry) {
        try {
            JournalMongoDBEntry oldEntry = journalMongodbEntryService.getEntryById(myId).orElse(null);
            if(oldEntry != null) {
                oldEntry.setTitle(!newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            }
            journalMongodbEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/entries")
    public ResponseEntity<?> getAllJournalEntries() {
        try {
            List<JournalMongoDBEntry> all = new ArrayList<>(journalEntries.values());
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/entrypoint/{myId}")
    public ResponseEntity<?> getAllJournalEntryById(@PathVariable ObjectId myId) {
        System.out.println("GetAllJournalEntryById called");
        try {
            JournalMongoDBEntry entry = journalMongodbEntryService.getEntryById(myId).orElse(null);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/entries")
    public ResponseEntity<Boolean> createEntry(@RequestBody JournalMongoDBEntry entry) {
        try {
            journalEntries.put(entry.getId(), entry);
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/entrypoint/{myId}")
    public ResponseEntity<Boolean> deleteEntryById(@PathVariable ObjectId myId) {
        try {
            journalEntries.remove(myId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/entrypoint/{myId}")
    public ResponseEntity<Boolean> updateEntryById(@RequestBody JournalMongoDBEntry entry, @PathVariable ObjectId myId) {
        try {
            journalEntries.put(myId, entry);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
