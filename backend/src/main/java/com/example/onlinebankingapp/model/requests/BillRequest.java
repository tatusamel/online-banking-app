package com.example.onlinebankingapp.model.requests;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.enums.BillStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;

@Data
public class BillRequest {
    private String name;
    private Double amount;
    private Date dueDate;
    private Long accountId;

    @Enumerated(EnumType.STRING)
    private BillStatus status;
}
