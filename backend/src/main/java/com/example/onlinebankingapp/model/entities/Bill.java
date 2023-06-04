package com.example.onlinebankingapp.model.entities;

import com.example.onlinebankingapp.model.enums.BillStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Bill {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double amount;
    private Date dueDate;
    private BillStatus status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


}
