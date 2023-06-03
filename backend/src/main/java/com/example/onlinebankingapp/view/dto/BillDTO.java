package com.example.onlinebankingapp.view.dto;

import lombok.Data;

@Data
public class BillDTO {

    private String name;
    private Double amount;
    private String dueDate;
    private Long accountId;
}
