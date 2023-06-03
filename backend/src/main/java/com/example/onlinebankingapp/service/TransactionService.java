package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.entities.Transaction;
import com.example.onlinebankingapp.model.enums.TransactionType;
import com.example.onlinebankingapp.model.repositories.AccountRepository;
import com.example.onlinebankingapp.model.repositories.TransactionRepository;
import com.example.onlinebankingapp.model.requests.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountService accountService)
    {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;

    }

    public List<Transaction> listAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("No Transaction with id: " + transactionId));
    }

    public Transaction insertTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();
        mapRequestToTransaction(request, transaction);
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long transactionId, TransactionRequest request) {
        Transaction transaction = this.getTransactionById(transactionId);
        mapRequestToTransaction(request, transaction);
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("No Transaction with id: " + transactionId));
        transactionRepository.delete(transaction);
    }

    public Transaction mapRequestToTransaction(TransactionRequest request, Transaction transaction) {
        Account fromAccount = accountService.getAccountById(request.getFromAccountId());
        Account toAccount = accountService.getAccountById(request.getToAccountId());

        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setTransactionType(TransactionType.valueOf(request.getTransactionType()));
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);

        return transaction;
    }


}
