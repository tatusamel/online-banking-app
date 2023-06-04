package com.example.onlinebankingapp.controller;


import com.example.onlinebankingapp.model.entities.User;
import com.example.onlinebankingapp.model.requests.LoginRequest;
import com.example.onlinebankingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    private final UserService userService;


    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        // Find the user by email
        User optionalUser = userService.login(loginRequest);
        if (optionalUser != null) {
            return ResponseEntity.ok(optionalUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
