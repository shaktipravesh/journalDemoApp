package com.shaktipravesh.journalDemoApp.controller;

import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.service.UsersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public List<User> getAllUsers() {
        return usersService.getAllUsers();
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User userInDB = usersService.getUserByUserName(user.getUserName());

        if(userInDB != null) {
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            usersService.saveNewUser(userInDB);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{objectId}")
    public ResponseEntity<?> findUserbyId(@PathVariable ObjectId objectId) {

        Optional<User> userInDB = usersService.getUserById(objectId);
        return new ResponseEntity<>(userInDB, userInDB.isPresent() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{objectId}")
    public ResponseEntity<?> deleteUserbyId(@PathVariable ObjectId objectId) {

        try {
            usersService.deleteUserById(objectId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
