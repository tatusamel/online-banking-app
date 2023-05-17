package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.requests.AccountRequest;
import com.example.onlinebankingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("listAllAccounts")
    public ResponseEntity<List<Account>> listAllAccounts() {
        return accountService.listAllAccounts();
    }

    @PostMapping("/insertAccount")
    public ResponseEntity<Account> insertAccount(AccountRequest accountRequest) {
        return accountService.insertAccount(accountRequest);
    }
}
