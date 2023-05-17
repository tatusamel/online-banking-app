package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
