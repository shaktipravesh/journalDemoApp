package com.shaktipravesh.journalDemoApp.controller;

import com.shaktipravesh.journalDemoApp.api.response.WeatherResponse;
import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.service.UsersService;
import com.shaktipravesh.journalDemoApp.service.WeatherStackService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UsersService usersService;
    final
    WeatherStackService weatherStackService;

    public UserController(UsersService usersService, WeatherStackService weatherStackService) {
        this.usersService = usersService;
        this.weatherStackService = weatherStackService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return usersService.getAllUsers();
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId objectId) {

        try {
            usersService.deleteUserById(objectId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/weather")
    public ResponseEntity<String> getWeatherStack() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherStackService.getWeather("New Delhi");
        if(weatherResponse != null) {
            String result = "Hello " + authentication.getName() + "!\\n" + "Weather feels like " + weatherResponse.toString();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Hello " + authentication.getName(), HttpStatus.OK);
    }

}
