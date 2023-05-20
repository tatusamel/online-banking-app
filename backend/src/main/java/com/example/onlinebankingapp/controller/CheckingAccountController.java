package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.CheckingAccount;
import com.example.onlinebankingapp.model.requests.CheckingAccountRequest;
import com.example.onlinebankingapp.service.CheckingAccountService;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkingaccount")
public class CheckingAccountController {

    @Autowired
    private CheckingAccountService checkingAccountService;

    public CheckingAccountController(CheckingAccountService checkingAccountService) {
        this.checkingAccountService = checkingAccountService;
    }

    @GetMapping
    public ResponseEntity<List<CheckingAccount>> listAllAccounts() {
        return checkingAccountService.listAllAccounts();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<CheckingAccount> getAccountById(@PathVariable Long accountId) {
        return checkingAccountService.getAccountById(accountId);
    }

    @PostMapping("/insert")
    public ResponseEntity<CheckingAccount> insertAccount(@RequestBody CheckingAccountRequest checkingAccountRequest) {
        return checkingAccountService.insertAccount(checkingAccountRequest);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<CheckingAccount> updateAccount(@PathVariable Long accountId, @RequestBody CheckingAccountRequest checkingAccountRequest) {
        return checkingAccountService.updateAccount(accountId, checkingAccountRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        return checkingAccountService.deleteAccount(accountId);
    }


}
