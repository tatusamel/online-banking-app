package com.example.onlinebankingapp.view.dto;

import lombok.Data;

@Data
public class CustomerDTO extends UserDTO{

        private String address;
        private String phoneNumber;
}
