package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.onlinebankingapp.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    @GetMapping
    public List<User> getAllUsers() {return userService.getAllUsers();}

    @GetMapping("/{userId}")
    public Optional<User> getUserById(@PathVariable Long userId) { return this.userService.getUserById(userId);}

    @PutMapping("/update/{userId}")
    public User updateUserById(@PathVariable Long userId, @RequestBody User user) {
        return userService.updateUserById(userId, user);
    }

}
