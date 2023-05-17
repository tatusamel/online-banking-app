package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.enums.AccountType;
import com.example.onlinebankingapp.model.repositories.AccountRepository;
import com.example.onlinebankingapp.model.repositories.BranchRepository;
import com.example.onlinebankingapp.model.repositories.CustomerRepository;
import com.example.onlinebankingapp.model.requests.AccountRequest;
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

    public ResponseEntity<Account> insertAccount(AccountRequest accountRequest) {
        Long customerId = accountRequest.getCustomerId();
        Long branchId = accountRequest.getBranchId();

        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Branch> branch = branchRepository.findById(branchId);

        if ( customer.isEmpty() || branch.isEmpty() ) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        Account newAccount;
        AccountType accountType = accountRequest.getAccountType();

        if ( accountType.equals( AccountType.CheckingAccount )) {
            newAccount = new CheckingAccount();
        }else if ( accountType.equals( AccountType.SavingAccount )) {
            newAccount = new SavingAccount();
        }else if ( accountType.equals( AccountType.CreditCardAccount)) {
            newAccount = new CreditCardAccount();
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        newAccount.setAccountNumber(accountRequest.getAccountNumber());
        newAccount.setBranch(branch.get());
        newAccount.setCustomer(customer.get());
        newAccount.setBalance(accountRequest.getBalance());

        return new ResponseEntity<>(accountRepository.save(newAccount),HttpStatus.OK);

    }
}
