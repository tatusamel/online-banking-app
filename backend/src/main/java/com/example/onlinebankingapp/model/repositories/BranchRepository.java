package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  BranchRepository extends JpaRepository<Branch, Long> {
}
