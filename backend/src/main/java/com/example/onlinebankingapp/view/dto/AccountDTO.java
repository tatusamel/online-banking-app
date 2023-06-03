package com.example.onlinebankingapp.view.dto;

import lombok.Data;

import java.util.Set;

@Data
public class AccountDTO {

    private String accountNumber;
    private Double balance;
    private String accountType;
    private Long customerId;
    private Long branchId;

    /*
    no need to show the following fields to the user
    private Set<Bill> bills;
    private Set<Loan> loans;
    private Set<Transaction> outgoingTransactions;
    private Set<Transaction> incomingTransactions;

     */

}
