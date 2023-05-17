package com.example.onlinebankingapp.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public class BaseAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private double balance;
    private AccountType accountType;
    private Long customerId;
    private Long branch_id;

    private enum AccountType {
        CheckingAccount,
        SavingAccount,
        CreditCardAccount
    }
}
