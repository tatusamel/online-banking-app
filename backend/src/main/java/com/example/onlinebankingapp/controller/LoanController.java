package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Loan;
import com.example.onlinebankingapp.model.requests.LoanRequest;
import com.example.onlinebankingapp.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> listAllLoans() {
        List<Loan> loans =  loanService.listAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @GetMapping("/{loan_id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable("loan_id") Long loanId) {
        Loan loan = loanService.getLoanById(loanId);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Loan> insertLoan(@RequestBody LoanRequest loanRequest) {
        Loan loan = loanService.insertLoan(loanRequest);
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    @PutMapping("/update/{loan_id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable("loan_id}") Long loanId, @RequestBody LoanRequest loanRequest) {
        Loan loan = loanService.updateLoan(loanId, loanRequest);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{loan_id}")
    public ResponseEntity<Loan> deleteLoan(@PathVariable("loan_id") Long loanId) {
        loanService.deleteLoan(loanId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
