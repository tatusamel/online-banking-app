package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface  BranchRepository extends JpaRepository<Branch, Long> {

}
