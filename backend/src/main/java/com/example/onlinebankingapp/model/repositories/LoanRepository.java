package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Transactional
    @Query("SELECT l from Loan l WHERE l.account.customer.id = :customerId")
    List<Loan> findLoansByCustomerId(Long customerId);
}
