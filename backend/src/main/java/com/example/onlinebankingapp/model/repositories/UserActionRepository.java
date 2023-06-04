package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserActionRepository extends JpaRepository<UserAction, Long> {


    @Transactional
    @Query("SELECT ua FROM UserAction ua WHERE ua.userId = :userId ORDER BY ua.timestamp DESC")
    List<UserAction> findTop10ByUserIdOrderByTimestampDesc(Long userId);



}
