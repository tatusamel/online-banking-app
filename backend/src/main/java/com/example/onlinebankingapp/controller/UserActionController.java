package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.UserAction;
import com.example.onlinebankingapp.service.UserActionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/actions")
public class UserActionController {

    private final UserActionService userActionService;

    public UserActionController(UserActionService userActionService) {
        this.userActionService = userActionService;
    }

    @GetMapping("")
    public List<UserAction> getAll() {
        return userActionService.getAll();
    }

    @GetMapping("/{userId}")
    public List<UserAction> getUserActionsByUserId(@PathVariable Long userId) {
        return userActionService.getUserActionsByUserId(userId);
    }
}
