package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.entities.SavingAccount;
import com.example.onlinebankingapp.model.requests.SavingAccountRequest;
import com.example.onlinebankingapp.service.SavingAccountService;
import com.example.onlinebankingapp.view.converter.SavingAccountDTOConverter;
import com.example.onlinebankingapp.view.dto.SavingAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/saving-accounts")
public class SavingAccountController {

    private final SavingAccountService accountService;
    private final SavingAccountDTOConverter savingAccountDTOConverter;
    @Autowired
    public SavingAccountController( SavingAccountService savingAccountService,
                                    SavingAccountDTOConverter savingAccountDTOConverter) {
        this.accountService = savingAccountService;
        this.savingAccountDTOConverter = savingAccountDTOConverter;
    }

    @GetMapping
    public ResponseEntity<List<SavingAccountDTO>> getAll() {
        List<SavingAccountDTO> accountDTOS = accountService.getAll()
                .stream()
                .map(savingAccountDTOConverter::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(accountDTOS, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<SavingAccountDTO> getAccountById(@PathVariable Long accountId ) {
        SavingAccountDTO savingAccountDTO = savingAccountDTOConverter.convertToDto(accountService.getAccountById(accountId));
        return new ResponseEntity<>(savingAccountDTO, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<SavingAccountDTO> insertAccount(@RequestBody SavingAccountRequest savingAccountRequest) {
        SavingAccountDTO savingAccountDTO = savingAccountDTOConverter.convertToDto(accountService.insertAccount(savingAccountRequest));
        return new ResponseEntity<>(savingAccountDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<SavingAccountDTO> updateAccount(@PathVariable Long accountId, @RequestBody SavingAccountRequest accountRequest) {
        SavingAccountDTO savingAccountDTO = savingAccountDTOConverter.convertToDto(accountService.updateAccount(accountId, accountRequest));
        return new ResponseEntity<>(savingAccountDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/min-balance")
    public ResponseEntity<Double> getMinBalance() {
        return new ResponseEntity<>(accountService.getMinBalance(), HttpStatus.OK);
    }

}
