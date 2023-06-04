package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.requests.LoginRequest;
import com.example.onlinebankingapp.model.entities.User;
import com.example.onlinebankingapp.model.repositories.UserRepository;
import com.example.onlinebankingapp.model.requests.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserActionService userActionService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserActionService userActionService,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userActionService = userActionService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Boolean existById(Long id) {
        return userRepository.existsById(id);
    }

    public User createUser(UserRequest request) {

        User user = new User();
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        userRepository.save(user);

        userActionService.userCreatedAction(user.getId());
        return user;
    }
    public User updateUser(Long userId, UserRequest request) {

        User user = this.getUserById(userId);

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        userActionService.userUpdatedAction(user.getId());
        return userRepository.save(user);

    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long userId) {
        User user = this.getUserById(userId);
        userActionService.userDeletedAction(userId);
        userRepository.delete(user);
    }

    public User login(LoginRequest loginRequest) {


        // Find the user by email
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Check if the password matches
            String passwordEntered = passwordEncoder.encode(loginRequest.getPassword());
            if (user.getPassword().equals(passwordEntered)) {
                // Password is correct, return the user object
                userActionService.userLoginSuccessfulAction(user.getId());
                return user;
            }
            userActionService.userLoginFailedAction(optionalUser.get().getId());
        }
        // Invalid credentials, return null
        return null;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

    public void deleteUserById(Long id) {
        if ( !userRepository.existsById(id) ) {
            throw new NoSuchElementException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
