package com.example.onlinebankingapp.model.entities;

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

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


}
