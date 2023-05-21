package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.entities.Bill;
import com.example.onlinebankingapp.model.entities.Loan;
import com.example.onlinebankingapp.model.repositories.AccountRepository;
import com.example.onlinebankingapp.model.repositories.BillRepository;
import com.example.onlinebankingapp.model.repositories.LoanRepository;
import com.example.onlinebankingapp.model.requests.BillRequest;
import com.example.onlinebankingapp.model.requests.LoanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, AccountRepository accountRepository) {
        this.loanRepository = loanRepository;
        this.accountRepository = accountRepository;
    }

    public List<Loan> listAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchElementException("No Loan with id: " + loanId));
    }

    public Loan insertLoan(LoanRequest loanRequest) {
        Account account = accountRepository.findById(loanRequest.getAccountId())
                .orElseThrow(() -> new NoSuchElementException("No Account with id: " + loanRequest.getAccountId()));

        Loan loan = new Loan();
        loan.setAccount(account);
        loan.setAmount(loanRequest.getAmount());
        loan.setInterestRate(loanRequest.getInterestRate());
        loan.setStartDate(loanRequest.getStartDate());
        loan.setEndDate(loanRequest.getEndDate());

        return loanRepository.save(loan);
    }

    public Loan updateLoan(Long loanId, LoanRequest loanRequest) {
        Account account = accountRepository.findById(loanRequest.getAccountId())
                .orElseThrow(() -> new NoSuchElementException("No Account with id: " + loanRequest.getAccountId()));
        Loan loanToUpdate = loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchElementException("No Loan with id: " + loanId));

        loanToUpdate.setAccount(account);
        loanToUpdate.setAmount(loanRequest.getAmount());
        loanToUpdate.setInterestRate(loanRequest.getInterestRate());
        loanToUpdate.setStartDate(loanRequest.getStartDate());
        loanToUpdate.setEndDate(loanRequest.getEndDate());

        return loanRepository.save(loanToUpdate);
    }

    public void deleteLoan(Long loanId) {
        Loan loanToDelete = loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchElementException("No Loan with id: " + loanId));
        loanRepository.delete(loanToDelete);
    }

}
