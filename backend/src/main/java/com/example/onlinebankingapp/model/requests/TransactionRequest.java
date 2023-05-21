package com.example.onlinebankingapp.model.requests;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionRequest {
    private double amount;
    private Date transactionDate;
    private Long transactionTypeId;
    private Long fromAccountId;
    private Long toAccountId;
}
