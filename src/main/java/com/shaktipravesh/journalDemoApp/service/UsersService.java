package com.shaktipravesh.journalDemoApp.service;

import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsersService {
    private final UserRepository usersRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsersService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("ROLE_USER"));
            usersRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Error Message {} {}", user.getUserName(), e.getMessage());
            log.warn("Warning Message {}", e.getMessage());
            log.info("Info Message {}", e.getMessage());
            log.debug("Debug Message {}", e.getMessage());
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
