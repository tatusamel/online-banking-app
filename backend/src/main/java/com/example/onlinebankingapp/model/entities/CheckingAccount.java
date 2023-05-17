package com.example.onlinebankingapp.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@DiscriminatorValue(value = "CheckingAccount")
@Data
public class CheckingAccount extends Account {
}
