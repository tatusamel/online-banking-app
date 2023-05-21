package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.entities.SavingAccount;
import com.example.onlinebankingapp.model.requests.SavingAccountRequest;
import com.example.onlinebankingapp.service.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savingaccount")
public class SavingAccountController {

    @Autowired
    private SavingAccountService accountService;

    @GetMapping
    public ResponseEntity<List<SavingAccount>> listAllAccounts() {
        return accountService.listAllAccounts();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<SavingAccount> getAccountById(@PathVariable Long accountId ) {
        return accountService.getAccountById(accountId);
    }

    @PostMapping("/insert")
    public ResponseEntity<SavingAccount> insertAccount(@RequestBody SavingAccountRequest savingAccountRequest) {
        return accountService.insertAccount(savingAccountRequest);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<SavingAccount> updateAccount(@PathVariable Long accountId, @RequestBody SavingAccountRequest accountRequest) {
        return accountService.updateAccount(accountId,accountRequest);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        return accountService.deleteAccount(accountId);
    }

}
