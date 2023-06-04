package com.example.onlinebankingapp.view.report;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.enums.AccountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CustomerAccountReport {

    private String customerName;
    private String accountNumber;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}
