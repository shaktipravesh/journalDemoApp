package com.shaktipravesh.journalDemoApp.service;

import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UsersService usersService;

    @Test
    public void testGetUserByUserName() {
        assertNotNull(userRepository.findByUserName("spravesh"));
        assertNull(userRepository.findByUserName("shaktiprs"));
    }

    @ParameterizedTest
    @CsvSource({
            "rathore",
            "suhasini",
    })
    public void testCsvSourceGetUserByUserNameNull(String userName) {
        assertNull(userRepository.findByUserName(userName), userName + "User not found");
    }

    @ParameterizedTest
    @CsvSource({
            "spravesh",
            "beena"
    })
    public void testCsvSourceGetUserByUserName(String userName) {
        assertNotNull(userRepository.findByUserName(userName), userName + "User not found");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "suhasini",
            "rathore"
    })
    public void testValueSourceGetUserByUserNameNull(String userName) {
        assertNull(userRepository.findByUserName(userName), userName + "User not found");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "spravesh",
            "beena"
    })
    public void testValueSourceGetUserByUserName(String userName) {
        assertNotNull(userRepository.findByUserName(userName), userName + "User not found");
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testArgumentsSourceSaveNewUser(User user) {
        assertTrue(usersService.saveNewUser(user));
        User userFromDB = userRepository.findByUserName(user.getUserName());
        assertNotNull(userFromDB);
        assertTrue(usersService.deleteUserById(userFromDB.getId()));

    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1, 1, 2",
            "2, 10, 12",
            "3, 3, 6"
    })
    public void testParameterized(int a, int b, int expected) {
        assertEquals(expected, a+b);
    }


//    @BeforeAll
//    @BeforeEach
//    @AfterAll
//    @AfterEach
}
