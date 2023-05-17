package com.example.onlinebankingapp.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue(value = "SavingAccount")
@Data
public class SavingAccount extends Account {
    private double interestRate;
}
