package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.CheckingAccount;
import com.example.onlinebankingapp.model.requests.CheckingAccountRequest;
import com.example.onlinebankingapp.service.CheckingAccountService;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkingaccount")
public class CheckingAccountController {


    private final CheckingAccountService checkingAccountService;

    @Autowired
    public CheckingAccountController(CheckingAccountService checkingAccountService) {
        this.checkingAccountService = checkingAccountService;
    }

    @GetMapping
    public ResponseEntity<List<CheckingAccount>> listAllAccounts() {
        List<CheckingAccount> accounts = checkingAccountService.listAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<CheckingAccount> getAccountById(@PathVariable Long accountId) {
        CheckingAccount account = checkingAccountService.getAccountById(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<CheckingAccount> insertAccount(@RequestBody CheckingAccountRequest checkingAccountRequest) {
        CheckingAccount account = checkingAccountService.insertAccount(checkingAccountRequest);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<CheckingAccount> updateAccount(@PathVariable Long accountId, @RequestBody CheckingAccountRequest checkingAccountRequest) {
        CheckingAccount account = checkingAccountService.updateAccount(accountId, checkingAccountRequest);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        checkingAccountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
