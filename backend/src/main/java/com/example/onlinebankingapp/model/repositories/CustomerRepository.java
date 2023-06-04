package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.model.entities.Transaction;
import com.example.onlinebankingapp.view.dto.TransactionDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Transactional
    @Query("SELECT t FROM Transaction t WHERE t.toAccount.customer.id = :customerId "+
            "OR t.fromAccount.customer.id = :customerId ORDER BY t.transactionDate DESC")
    List<Transaction> find10MostRecentTransactionsByCustomerId(Long customerId, Pageable pageable);

    @Transactional
    @Query("SELECT COUNT(c) FROM Customer c")
    Integer findNumberOfCustomers();
}
