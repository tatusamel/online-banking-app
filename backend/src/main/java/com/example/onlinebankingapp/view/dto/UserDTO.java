package com.example.onlinebankingapp.view.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
