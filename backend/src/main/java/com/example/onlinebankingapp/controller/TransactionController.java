package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Transaction;
import com.example.onlinebankingapp.model.requests.TransactionRequest;
import com.example.onlinebankingapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> listAllTransactions() {
        List<Transaction> transactions = transactionService.listAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Transaction> insertTransaction(@RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = transactionService.insertTransaction(transactionRequest);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long transactionId, @RequestBody TransactionRequest transactionRequest) {
        Transaction updatedTransaction = transactionService.updateTransaction(transactionId, transactionRequest);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
