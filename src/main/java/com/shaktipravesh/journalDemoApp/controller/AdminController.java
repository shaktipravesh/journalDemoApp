package com.shaktipravesh.journalDemoApp.controller;

import com.shaktipravesh.journalDemoApp.cache.AppCache;
import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private AppCache appCache;

    @RequestMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = usersService.getAllUsers();
        if(users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("create-admin-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            user.setRoles(roles);
            usersService.saveNewUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("reload-app-cache")
    public ResponseEntity<?> getAppCache() {
        try {
            appCache.init();
            return new ResponseEntity<>(appCache, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
