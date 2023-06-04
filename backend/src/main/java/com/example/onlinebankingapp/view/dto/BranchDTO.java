package com.example.onlinebankingapp.view.dto;

import lombok.Data;

import java.util.Set;

@Data
public class BranchDTO {
    private Long branchId;
    private String name;
    private String address;
    private Set<Long> accountIds;
}
