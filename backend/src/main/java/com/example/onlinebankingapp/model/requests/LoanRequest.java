package com.example.onlinebankingapp.model.requests;

import lombok.Data;

import java.util.Date;

@Data
public class LoanRequest {
    private Double amount;
    private Double interestRate;
    private Date startDate;
    private Date endDate;
    private Long accountId;
}
