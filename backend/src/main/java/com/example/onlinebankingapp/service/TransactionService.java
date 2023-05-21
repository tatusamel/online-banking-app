package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.entities.Transaction;
import com.example.onlinebankingapp.model.entities.TransactionType;
import com.example.onlinebankingapp.model.repositories.AccountRepository;
import com.example.onlinebankingapp.model.repositories.TransactionRepository;
import com.example.onlinebankingapp.model.repositories.TransactionTypeRepository;
import com.example.onlinebankingapp.model.requests.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionTypeRepository transactionTypeRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              TransactionTypeRepository transactionTypeRepository)
    {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public List<Transaction> listAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("No Transaction with id: " + transactionId));
    }

    public Transaction insertTransaction(TransactionRequest request) {
        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new NoSuchElementException("No Account with id: " + request.getFromAccountId()));
        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new NoSuchElementException("No Account with id: " + request.getToAccountId()));
        TransactionType transactionType = transactionTypeRepository.findById(request.getTransactionTypeId())
                .orElseThrow(() -> new NoSuchElementException("No TransactionType with id: " + request.getTransactionTypeId()));

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setTransactionType(transactionType);
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long transactionId, TransactionRequest request) {
        Transaction transactionToUpdate = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("No Transaction with id: " + transactionId));
        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new NoSuchElementException("No Account with id: " + request.getFromAccountId()));
        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new NoSuchElementException("No Account with id: " + request.getToAccountId()));
        TransactionType transactionType = transactionTypeRepository.findById(request.getTransactionTypeId())
                .orElseThrow(() -> new NoSuchElementException("No TransactionType with id: " + request.getTransactionTypeId()));

        transactionToUpdate.setAmount(request.getAmount());
        transactionToUpdate.setTransactionDate(request.getTransactionDate());
        transactionToUpdate.setTransactionType(transactionType);
        transactionToUpdate.setFromAccount(fromAccount);
        transactionToUpdate.setToAccount(toAccount);

        return transactionRepository.save(transactionToUpdate);
    }

    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("No Transaction with id: " + transactionId));
        transactionRepository.delete(transaction);
    }


}
