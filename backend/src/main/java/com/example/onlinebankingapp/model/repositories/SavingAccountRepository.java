package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAccountRepository extends JpaRepository<SavingAccount, Long> {
}
