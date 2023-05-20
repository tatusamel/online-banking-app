package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.enums.AccountType;
import com.example.onlinebankingapp.model.repositories.AccountRepository;
import com.example.onlinebankingapp.model.repositories.BranchRepository;
import com.example.onlinebankingapp.model.repositories.CustomerRepository;
import com.example.onlinebankingapp.model.repositories.SavingAccountRepository;
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
public class SavingAccountService {

    @Autowired
    private SavingAccountRepository savingAccountRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public SavingAccountService(SavingAccountRepository savingAccountRepository) {
        this.savingAccountRepository = savingAccountRepository;
    }

    public ResponseEntity<List<SavingAccount>> listAllAccounts() {
        return new ResponseEntity<>(savingAccountRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<SavingAccount> getAccountById(Long accountId) {
        Optional<SavingAccount> account = savingAccountRepository.findById(accountId);
        if ( account.isEmpty() ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account.get(), HttpStatus.FOUND);
    }

    public ResponseEntity<SavingAccount> insertAccount(SavingAccountRequest accountRequest) {

        Long customerId = accountRequest.getCustomerId();
        Long branchId = accountRequest.getBranchId();

        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Branch> branch = branchRepository.findById(branchId);

        if ( customer.isEmpty() || branch.isEmpty() ) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        SavingAccount newAccount = new SavingAccount();

        newAccount.setAccountNumber(accountRequest.getAccountNumber());
        newAccount.setBranch(branch.get());
        newAccount.setCustomer(customer.get());
        newAccount.setBalance(accountRequest.getBalance());
        newAccount.setInterestRate(accountRequest.getInterestRate());

        return new ResponseEntity<>(savingAccountRepository.save(newAccount),HttpStatus.CREATED);

    }

    public ResponseEntity<SavingAccount> updateAccount(Long accountId, SavingAccountRequest accountRequest) {
        Optional<SavingAccount> account = savingAccountRepository.findById(accountId);
        Optional<Customer> customer = customerRepository.findById(accountRequest.getCustomerId());
        Optional<Branch> branch = branchRepository.findById(accountRequest.getBranchId());

        if ( account.isEmpty() || customer.isEmpty() || branch.isEmpty() ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        SavingAccount accountToUpdate = account.get();

        accountToUpdate.setBalance(accountRequest.getBalance());
        accountToUpdate.setAccountNumber(accountRequest.getAccountNumber());
        accountToUpdate.setCustomer(customer.get());
        accountToUpdate.setBranch(branch.get());
        accountToUpdate.setInterestRate(accountRequest.getInterestRate());

        return new ResponseEntity<>(savingAccountRepository.save(accountToUpdate), HttpStatus.OK);

    }

    public ResponseEntity<Void> deleteAccount(Long accountId) {
        Optional<SavingAccount> account = savingAccountRepository.findById(accountId);
        if ( account.isEmpty() ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        savingAccountRepository.delete(account.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
