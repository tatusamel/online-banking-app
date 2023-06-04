package com.example.onlinebankingapp.model.requests;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionRequest {
    private Double amount;
    private Long fromAccountId;
    private Long toAccountId;
}
