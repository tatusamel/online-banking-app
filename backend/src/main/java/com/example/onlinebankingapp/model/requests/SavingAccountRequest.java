package com.example.onlinebankingapp.model.requests;

import lombok.Data;

@Data
public class SavingAccountRequest extends AccountRequest {
    private double interestRate;
}
