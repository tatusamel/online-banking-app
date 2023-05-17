package com.example.onlinebankingapp.model.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private double interestRate;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
