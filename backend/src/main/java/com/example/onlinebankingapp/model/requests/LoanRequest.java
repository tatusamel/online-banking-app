package com.example.onlinebankingapp.model.requests;

import lombok.Data;

import java.util.Date;

@Data
public class LoanRequest {
    private double amount;
    private double interestRate;
    private Date startDate;
    private Date endDate;
    private Long accountId;

}
