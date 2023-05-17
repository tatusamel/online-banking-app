package com.example.onlinebankingapp.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "ACCOUNT_TYPE",
        discriminatorType = DiscriminatorType.STRING
)
@Data
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private double balance;

    @ManyToOne(targetEntity = Customer.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(targetEntity = Branch.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id")
    private Branch branch;

}
