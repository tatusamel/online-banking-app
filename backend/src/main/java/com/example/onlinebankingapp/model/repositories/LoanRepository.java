package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

}
