package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
