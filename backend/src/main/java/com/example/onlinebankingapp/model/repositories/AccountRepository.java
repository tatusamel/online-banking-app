package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Transactional
    @Query("SELECT a FROM Account a WHERE a.customer.id = :customerId")
    List<Account> findAccountsByCustomerId(@Param("customerId") Long customerId);

    @Transactional
    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
    Optional<Account> findAccountByAccountNumber(String accountNumber);
}
