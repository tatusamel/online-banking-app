package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.User;
import com.example.onlinebankingapp.model.requests.UserRequest;
import com.example.onlinebankingapp.view.converter.UserDTOConverter;
import com.example.onlinebankingapp.view.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.onlinebankingapp.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserDTOConverter userDTOConverter;

    @Autowired
    public UserController(UserService userService,
                          UserDTOConverter userDTOConverter ) {
        this.userService = userService;
        this.userDTOConverter = userDTOConverter;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream()
                .map(userDTOConverter::convertToDto).
                collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        UserDTO userDTO = userDTOConverter.convertToDto(userService.getUserById(id));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequest request) {
        UserDTO userDTO = userDTOConverter.convertToDto(userService.createUser(request));
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserRequest request) {
        UserDTO userDTO = userDTOConverter.convertToDto(userService.updateUser(id, request));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
