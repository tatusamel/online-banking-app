package com.example.onlinebankingapp.model.requests;

import com.example.onlinebankingapp.model.enums.AccountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class AccountRequest {
    private String accountNumber;
    private double balance;
    private Long customerId;
    private Long branchId;

    //@Enumerated(EnumType.STRING)
    //private AccountType accountType;

}
