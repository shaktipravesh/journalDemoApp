package com.shaktipravesh.journalDemoApp.controller;

import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UsersService usersService;

    @PostMapping("/create-user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            if(usersService.saveNewUser(user)) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
