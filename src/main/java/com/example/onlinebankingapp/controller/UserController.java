package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.onlinebankingapp.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    @GetMapping
    public List<User> getAllUsers() {return userService.getAllUsers();}

    @GetMapping("/{userId}")
    public Optional<User> getUserById(@PathVariable Long userId) { return this.userService.getUserById(userId);}

    @GetMapping("/sayHello")
    public String sayHello(){
        return "hello world";
    }

}
