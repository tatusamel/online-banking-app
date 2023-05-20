package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.CreditCardAccount;
import com.example.onlinebankingapp.model.entities.SavingAccount;
import com.example.onlinebankingapp.model.requests.CreditCardAccountRequest;
import com.example.onlinebankingapp.model.requests.SavingAccountRequest;
import com.example.onlinebankingapp.service.CreditCardAccountService;
import com.example.onlinebankingapp.service.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/creditcardaccount")
public class CreditCardAccountController {

    @Autowired
    private CreditCardAccountService creditCardAccountService;

    @GetMapping
    public ResponseEntity<List<CreditCardAccount>> listAllAccounts(){
        return creditCardAccountService.listAllAccounts();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<CreditCardAccount> getAccountById(@PathVariable Long accountId) {
        return creditCardAccountService.getAccountById(accountId);
    }

    @PostMapping("/insert")
    public ResponseEntity<CreditCardAccount> insertAccount(@RequestBody CreditCardAccountRequest creditCardAccountRequest) {
        return creditCardAccountService.insertAccount(creditCardAccountRequest);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<CreditCardAccount> updateAccount(@PathVariable Long accountId, @RequestBody CreditCardAccountRequest accountRequest) {
        return creditCardAccountService.updateAccount(accountId,accountRequest);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        return creditCardAccountService.deleteAccount(accountId);
    }

}
