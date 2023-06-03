package com.example.onlinebankingapp.view.dto;

import lombok.Data;

@Data
public class CreditCardAccountDTO extends AccountDTO{

        private Double creditLimit;
        private Double interestRate;
}
