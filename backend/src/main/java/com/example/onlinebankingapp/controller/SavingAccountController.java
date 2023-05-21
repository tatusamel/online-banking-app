package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.entities.SavingAccount;
import com.example.onlinebankingapp.model.requests.SavingAccountRequest;
import com.example.onlinebankingapp.service.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saving-accounts")
public class SavingAccountController {

    private final SavingAccountService accountService;
    @Autowired
    public SavingAccountController( SavingAccountService savingAccountService ) {
        this.accountService = savingAccountService;
    }

    @GetMapping
    public ResponseEntity<List<SavingAccount>> listAllAccounts() {
        List<SavingAccount> accounts = accountService.listAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<SavingAccount> getAccountById(@PathVariable Long accountId ) {
        SavingAccount savingAccount = accountService.getAccountById(accountId);
        return new ResponseEntity<>(savingAccount, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<SavingAccount> insertAccount(@RequestBody SavingAccountRequest savingAccountRequest) {
        SavingAccount savingAccount = accountService.insertAccount(savingAccountRequest);
        return new ResponseEntity<>(savingAccount, HttpStatus.CREATED);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<SavingAccount> updateAccount(@PathVariable Long accountId, @RequestBody SavingAccountRequest accountRequest) {
        SavingAccount savingAccount = accountService.updateAccount(accountId, accountRequest);
        return new ResponseEntity<>(savingAccount, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
