package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.TransactionType;
import com.example.onlinebankingapp.service.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction-types")
public class TransactionTypeController {

    private final TransactionTypeService transactionTypeService;

    @Autowired
    public TransactionTypeController(TransactionTypeService transactionTypeService) {
        this.transactionTypeService = transactionTypeService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionType>> listAllTransactionTypes() {
        List<TransactionType> transactionTypes = transactionTypeService.listAllTransactionTypes();
        return new ResponseEntity<>(transactionTypes, HttpStatus.OK);
    }

    @GetMapping("/{transactionTypeId}")
    public ResponseEntity<TransactionType> getTransactionTypeById(@PathVariable Long transactionTypeId) {
        TransactionType transactionType = transactionTypeService.getTransactionTypeById(transactionTypeId);
        return new ResponseEntity<>(transactionType, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionType> insertTransactionType(@RequestBody TransactionType transactionType) {
        TransactionType newTransactionType = transactionTypeService.insertTransactionType(transactionType);
        return new ResponseEntity<>(newTransactionType, HttpStatus.CREATED);
    }

    @PutMapping("/{transactionTypeId}")
    public ResponseEntity<TransactionType> updateTransactionType(@PathVariable Long transactionTypeId, @RequestBody TransactionType transactionType) {
        TransactionType updatedTransactionType = transactionTypeService.updateTransactionType(transactionTypeId, transactionType);
        return new ResponseEntity<>(updatedTransactionType, HttpStatus.OK);
    }

    @DeleteMapping("/{transactionTypeId}")
    public ResponseEntity<Void> deleteTransactionType(@PathVariable Long transactionTypeId) {
        transactionTypeService.deleteTransactionType(transactionTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
