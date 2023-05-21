package com.example.onlinebankingapp.model.requests;

import com.example.onlinebankingapp.model.entities.Account;
import lombok.Data;

import java.util.Date;

@Data
public class BillRequest {
    private Long id;
    private String name;
    private double amount;
    private Date dueDate;
    private Long accountId;
}
