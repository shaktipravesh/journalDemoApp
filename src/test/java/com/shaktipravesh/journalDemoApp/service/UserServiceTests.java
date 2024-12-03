package com.shaktipravesh.journalDemoApp.service;

import com.shaktipravesh.journalDemoApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testAddUser() {
        assertEquals(4, 2+2);
    }

    @Test
    public void testGetUserByUserName() {
        assertNotNull(userRepository.findByUserName("spravesh"));
        assertNull(userRepository.findByUserName("shaktiprs"));
    }
}
