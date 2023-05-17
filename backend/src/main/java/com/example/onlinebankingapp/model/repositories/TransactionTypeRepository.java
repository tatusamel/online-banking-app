package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
}
