package com.example.onlinebankingapp.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class CreditCardAccount extends Account {
    private double interestRate;
    private int creditLimit;
}
