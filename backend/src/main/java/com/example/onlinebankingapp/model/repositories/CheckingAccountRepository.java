package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount,Long> {
}
