package com.example.onlinebankingapp.view.dto;

import com.example.onlinebankingapp.model.enums.TransactionType;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionDTO {

    private Double amount;
    private Date transactionDate;
    private TransactionType TransactionType;
    private Long fromAccountId;
    private Long toAccountId;
}
