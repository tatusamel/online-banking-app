package com.example.onlinebankingapp.model.requests;

import com.example.onlinebankingapp.model.entities.Branch;
import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.model.enums.AccountType;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class AccountRequest {
    private String accountNumber;
    private double balance;
    private Long customerId;
    private Long branchId;
    private AccountType accountType;

}
