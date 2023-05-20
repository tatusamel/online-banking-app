package com.example.onlinebankingapp.model.requests;

import lombok.Data;

@Data
public class CreditCardAccountRequest extends AccountRequest{
    private double interestRate;
    private int creditLimit;
}
