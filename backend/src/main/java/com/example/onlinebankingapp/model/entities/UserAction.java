package com.example.onlinebankingapp.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class UserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String action;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public UserAction() {
        this.timestamp = new Date();
    }

    public UserAction(
            Long userId,
            String action)
    {
        this.action = action;
        this.userId = userId;
        this.timestamp = new Date();
    }

}
