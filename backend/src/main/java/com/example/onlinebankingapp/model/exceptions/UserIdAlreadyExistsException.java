package com.example.onlinebankingapp.model.exceptions;

public class UserIdAlreadyExistsException extends RuntimeException{
    public UserIdAlreadyExistsException(String message) {
        super(message);
    }
}
