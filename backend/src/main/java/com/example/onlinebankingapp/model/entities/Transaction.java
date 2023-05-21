package com.example.onlinebankingapp.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private Date transactionDate;

    @ManyToOne(targetEntity = TransactionType.class)
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;



}
