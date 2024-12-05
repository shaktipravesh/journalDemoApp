package com.shaktipravesh.journalDemoApp.service;

import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsersService {
    @Autowired
    private UserRepository usersRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            usersRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateUserJournalEntries(User user) {
        usersRepository.save(user);
    }

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id) {
        return usersRepository.findById(id);
    }

    public boolean deleteUserById(ObjectId id) {
        try {
            usersRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User getUserByUserName(String username) {
        return usersRepository.findByUserName(username);
    }

}
