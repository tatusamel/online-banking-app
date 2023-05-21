package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.CreditCardAccount;
import com.example.onlinebankingapp.model.entities.SavingAccount;
import com.example.onlinebankingapp.model.requests.CreditCardAccountRequest;
import com.example.onlinebankingapp.model.requests.SavingAccountRequest;
import com.example.onlinebankingapp.service.CreditCardAccountService;
import com.example.onlinebankingapp.service.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/creditcardaccount")
public class CreditCardAccountController {

    private final CreditCardAccountService creditCardAccountService;
    @Autowired
    public CreditCardAccountController(CreditCardAccountService creditCardAccountService) {
        this.creditCardAccountService = creditCardAccountService;
    }

    @GetMapping
    public ResponseEntity<List<CreditCardAccount>> listAllAccounts(){
        List<CreditCardAccount> accounts = creditCardAccountService.listAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<CreditCardAccount> getAccountById(@PathVariable Long accountId) {
        CreditCardAccount creditCardAccount = creditCardAccountService.getAccountById(accountId);
        return new ResponseEntity<>(creditCardAccount, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<CreditCardAccount> insertAccount(@RequestBody CreditCardAccountRequest creditCardAccountRequest) {
        CreditCardAccount creditCardAccount = creditCardAccountService.insertAccount(creditCardAccountRequest);
        return new ResponseEntity<>(creditCardAccount, HttpStatus.CREATED);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<CreditCardAccount> updateAccount(@PathVariable Long accountId, @RequestBody CreditCardAccountRequest accountRequest) {
        CreditCardAccount creditCardAccount =  creditCardAccountService.updateAccount(accountId,accountRequest);
        return new ResponseEntity<>(creditCardAccount, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        creditCardAccountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
