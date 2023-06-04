package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.entities.Bill;
import com.example.onlinebankingapp.model.entities.Loan;
import com.example.onlinebankingapp.model.repositories.AccountRepository;
import com.example.onlinebankingapp.model.repositories.BillRepository;
import com.example.onlinebankingapp.model.repositories.LoanRepository;
import com.example.onlinebankingapp.model.requests.BillRequest;
import com.example.onlinebankingapp.model.requests.LoanRequest;
import com.example.onlinebankingapp.view.converter.LoanDTOConverter;
import com.example.onlinebankingapp.view.dto.LoanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final AccountService accountService;
    private final LoanDTOConverter loanDTOConverter;

    @Autowired
    public LoanService(LoanRepository loanRepository,
                       AccountService accountService,
                       LoanDTOConverter loanDTOConverter) {
        this.loanRepository = loanRepository;
        this.accountService = accountService;
        this.loanDTOConverter = loanDTOConverter;
    }

    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchElementException("No Loan with id: " + loanId));
    }

    public Loan insertLoan(LoanRequest loanRequest) {
        Loan loan = new Loan();
        mapRequestToLoan(loanRequest, loan);
        return loanRepository.save(loan);
    }

    public Loan updateLoan(Long loanId, LoanRequest loanRequest) {
        Loan loanToUpdate = this.getLoanById(loanId);
        mapRequestToLoan(loanRequest, loanToUpdate);
        return loanRepository.save(loanToUpdate);
    }

    public void deleteLoan(Long loanId) {
        Loan loanToDelete = getLoanById(loanId);
        loanRepository.delete(loanToDelete);
    }

    public Loan mapRequestToLoan(LoanRequest loanRequest, Loan loan) {
        Account account = accountService.getAccountById(loanRequest.getAccountId());

        loan.setAccount(account);
        loan.setAmount(loanRequest.getAmount());
        loan.setInterestRate(loanRequest.getInterestRate());
        loan.setStartDate(loanRequest.getStartDate());
        loan.setEndDate(loanRequest.getEndDate());

        return loan;
    }

    public List<LoanDTO> getLoansByCustomerId(Long customerId) {
        return loanRepository.findLoansByCustomerId(customerId)
                .stream()
                .map(loanDTOConverter::convertToDto)
                .collect(Collectors.toList());
    }
}
