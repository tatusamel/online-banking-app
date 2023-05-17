package com.example.onlinebankingapp.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@DiscriminatorValue(value = "CHECKING_ACCOUNT")
@Data
public class CheckingAccount extends Account {
}
