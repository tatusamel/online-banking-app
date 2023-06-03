package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.view.dto.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
