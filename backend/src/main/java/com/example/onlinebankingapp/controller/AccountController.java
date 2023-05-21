package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.requests.AccountRequest;
import com.example.onlinebankingapp.service.AccountService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public ResponseEntity<List<Account>> listAllAccounts() {
        List<Account> accounts = accountService.listAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Account> insertAccount(@RequestBody AccountRequest accountRequest) {
        Account account = accountService.insertAccount(accountRequest);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @RequestBody AccountRequest accountRequest) {
        Account account =  accountService.updateAccount(accountId,accountRequest);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
