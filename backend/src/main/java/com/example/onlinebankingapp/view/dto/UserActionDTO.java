package com.example.onlinebankingapp.view.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserActionDTO {

    private Long id;
    private Long userId;
    private String action;
    private Date timestamp;
}
