package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.User;
import com.example.onlinebankingapp.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {return userRepository.findAll();}

    public Optional<User> getUserById(Long id) {return userRepository.findById(id);}


}
