package com.example.onlinebankingapp.model.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private Date date;

    @ManyToOne(targetEntity = TransactionType.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;

    @ManyToOne(targetEntity = Account.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne(targetEntity = Account.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;



}
