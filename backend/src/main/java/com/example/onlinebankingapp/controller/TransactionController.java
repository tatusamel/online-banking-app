package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.requests.TransactionRequest;
import com.example.onlinebankingapp.service.TransactionService;
import com.example.onlinebankingapp.view.converter.TransactionDTOConverter;
import com.example.onlinebankingapp.view.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionDTOConverter transactionDTOConverter;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 TransactionDTOConverter transactionDTOConverter) {
        this.transactionService = transactionService;
        this.transactionDTOConverter = transactionDTOConverter;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAll() {
        List<TransactionDTO> transactionDTOS = transactionService.getAll()
                .stream()
                .map(transactionDTOConverter::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(transactionDTOS, HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long transactionId) {
        TransactionDTO transactionDTO = transactionDTOConverter.convertToDto(transactionService.getTransactionById(transactionId));
        return new ResponseEntity<>(transactionDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> insertTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionDTO transactionDTO = transactionDTOConverter.convertToDto(transactionService.createTransaction(transactionRequest));
        return new ResponseEntity<>(transactionDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long transactionId, @RequestBody TransactionRequest transactionRequest) {
        TransactionDTO transactionDTO = transactionDTOConverter.convertToDto(transactionService.updateTransaction(transactionId, transactionRequest));
        return new ResponseEntity<>(transactionDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/transactions/{accountNumber}")
    public List<TransactionDTO> getTransactionsByAccountNumber(@PathVariable String accountNumber) {
        return transactionService.getAllTransactionsByAccountNumber(accountNumber);
    }


}
