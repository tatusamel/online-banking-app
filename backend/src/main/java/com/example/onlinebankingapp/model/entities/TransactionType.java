package com.example.onlinebankingapp.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TransactionType {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
