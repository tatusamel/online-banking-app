package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SavingAccountRepository extends JpaRepository<SavingAccount, Long> {
    @Transactional
    @Query("SELECT min(a.balance) FROM SavingAccount a")
    Double findMinBalance();
}
