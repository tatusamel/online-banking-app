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

    public User updateUserById(Long id, User newUser){
        Optional<User> user = userRepository.findById(id);

        if ( user.isPresent() ) {
            User foundUser = user.get();
            foundUser.setFirstName(newUser.getFirstName());
            foundUser.setLastName(newUser.getLastName());
            foundUser.setEmail(newUser.getEmail());
            foundUser.setPassword(newUser.getPassword());
            return userRepository.save(foundUser);
        }
        return null;
    }


}
