package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.view.dto.TransactionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterPageService {

    /*
    Transactions by account number:

    This filter allows users to view all transactions associated with a specific account number. This is useful for customers
     with multiple accounts who want to check activity for one particular account.
     */
     // TODO: Implement this method
     public List<TransactionDTO> getTransactionsByAccountNumber(String accountNumber) {
         return null;
     }

}
