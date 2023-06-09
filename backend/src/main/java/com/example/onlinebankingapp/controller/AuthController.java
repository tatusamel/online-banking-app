package com.example.onlinebankingapp.controller;


import com.example.onlinebankingapp.model.entities.User;
import com.example.onlinebankingapp.model.requests.LoginRequest;
import com.example.onlinebankingapp.service.UserActionService;
import com.example.onlinebankingapp.service.UserService;
import com.example.onlinebankingapp.view.converter.UserDTOConverter;
import com.example.onlinebankingapp.view.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    private final UserService userService;
    private final UserActionService userActionService;
    private final UserDTOConverter userDTOConverter;


    @Autowired
    public AuthController(UserService userService,
                          UserActionService userActionService,
                          UserDTOConverter userDTOConverter) {
        this.userService = userService;
        this.userActionService = userActionService;
        this.userDTOConverter = userDTOConverter;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest) {
        // Find the user by email
        User user = userService.login(loginRequest);
        if (user != null) {
            return ResponseEntity.ok(userDTOConverter.convertToDto(user));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
