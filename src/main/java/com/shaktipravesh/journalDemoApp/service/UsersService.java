package com.shaktipravesh.journalDemoApp.service;

import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsersService {
    @Autowired
    private UserRepository usersRepository;



    public void saveEntry(User entry) {
        usersRepository.save(entry);
    }

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id) {
        return usersRepository.findById(id);
    }

    public void deleteUserById(ObjectId id) {
        usersRepository.deleteById(id);
    }

    public User getUserByUserName(String username) {
        return usersRepository.findByUserName(username);
    }
}


//controller --> service --> repository