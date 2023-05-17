package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.BaseAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseAccountRepository extends JpaRepository<BaseAccount, Long> {
}
