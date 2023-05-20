package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.enums.AccountType;
import com.example.onlinebankingapp.model.repositories.AccountRepository;
import com.example.onlinebankingapp.model.repositories.BranchRepository;
import com.example.onlinebankingapp.model.repositories.CustomerRepository;
import com.example.onlinebankingapp.model.requests.AccountRequest;
import com.example.onlinebankingapp.model.requests.CreditCardAccountRequest;
import com.example.onlinebankingapp.model.requests.SavingAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<List<Account>> listAllAccounts() {
        return new ResponseEntity<>(accountRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Account> getAccountById(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if ( account.isEmpty() ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    public ResponseEntity<Account> insertAccount(AccountRequest accountRequest) {
        Long customerId = accountRequest.getCustomerId();
        Long branchId = accountRequest.getBranchId();

        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Branch> branch = branchRepository.findById(branchId);

        if ( customer.isEmpty() || branch.isEmpty() ) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        Account newAccount = new Account();
        newAccount.setAccountNumber(accountRequest.getAccountNumber());
        newAccount.setBranch(branch.get());
        newAccount.setCustomer(customer.get());
        newAccount.setBalance(accountRequest.getBalance());

        return new ResponseEntity<>(accountRepository.save(newAccount),HttpStatus.CREATED);

    }

    public ResponseEntity<Account> updateAccount(Long accountId, AccountRequest accountRequest) {
        Optional<Account> account = accountRepository.findById(accountId);
        Optional<Customer> customer = customerRepository.findById(accountRequest.getCustomerId());
        Optional<Branch> branch = branchRepository.findById(accountRequest.getBranchId());

        if ( account.isEmpty() || customer.isEmpty() || branch.isEmpty() ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Account accountToUpdate = account.get();

        accountToUpdate.setBalance(accountRequest.getBalance());
        accountToUpdate.setAccountNumber(accountRequest.getAccountNumber());
        accountToUpdate.setCustomer(customer.get());
        accountToUpdate.setBranch(branch.get());

        return new ResponseEntity<>(accountRepository.save(accountToUpdate), HttpStatus.OK);

    }

    public ResponseEntity<Void> deleteAccount(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if ( account.isEmpty() ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        accountRepository.delete(account.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
