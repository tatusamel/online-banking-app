package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Loan;
import com.example.onlinebankingapp.model.requests.LoanRequest;
import com.example.onlinebankingapp.service.LoanService;
import com.example.onlinebankingapp.view.converter.LoanDTOConverter;
import com.example.onlinebankingapp.view.dto.LoanDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;
    private final LoanDTOConverter loanDTOConverter;

    public LoanController(LoanService loanService,
                          LoanDTOConverter loanDTOConverter) {
        this.loanService = loanService;
        this.loanDTOConverter = loanDTOConverter;
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> listAllLoans() {
        List<LoanDTO> loanDTOS =  loanService.listAllLoans()
                .stream()
                .map(loanDTOConverter::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(loanDTOS, HttpStatus.OK);
    }

    @GetMapping("/{loan_id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable("loan_id") Long loanId) {
        LoanDTO loanDTO = loanDTOConverter.convertToDto(loanService.getLoanById(loanId));
        return new ResponseEntity<>(loanDTO, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<LoanDTO> insertLoan(@RequestBody LoanRequest loanRequest) {
        LoanDTO loanDTO = loanDTOConverter.convertToDto(loanService.insertLoan(loanRequest));
        return new ResponseEntity<>(loanDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{loan_id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable("loan_id") Long loanId, @RequestBody LoanRequest loanRequest) {
        LoanDTO loanDTO = loanDTOConverter.convertToDto(loanService.updateLoan(loanId, loanRequest));
        return new ResponseEntity<>(loanDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{loan_id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable("loan_id") Long loanId) {
        loanService.deleteLoan(loanId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
