package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.CreditCardAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardAccountRepository extends JpaRepository<CreditCardAccount, Long> {
}
