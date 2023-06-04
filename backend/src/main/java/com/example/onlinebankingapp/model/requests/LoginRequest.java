package com.example.onlinebankingapp.model.requests;

import lombok.Data;
import lombok.Getter;


@Data
public class LoginRequest {
    @Getter
    private String email;
    @Getter
    private String password;
}
