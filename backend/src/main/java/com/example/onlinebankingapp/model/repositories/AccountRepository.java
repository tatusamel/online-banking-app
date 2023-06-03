package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    void
}
