package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
