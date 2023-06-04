package com.example.onlinebankingapp.view.dto;

import com.example.onlinebankingapp.model.enums.BillStatus;
import lombok.Data;

@Data
public class BillDTO {
    private BillStatus status;
    private String name;
    private Double amount;
    private String dueDate;
    private Long accountId;
    private Boolean isPaid;
}
