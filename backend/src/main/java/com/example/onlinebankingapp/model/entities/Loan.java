package com.example.onlinebankingapp.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private Double interestRate;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
