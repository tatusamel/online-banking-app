package com.example.onlinebankingapp.service;


import com.example.onlinebankingapp.model.entities.Branch;
import com.example.onlinebankingapp.model.entities.CreditCardAccount;
import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.model.repositories.BranchRepository;
import com.example.onlinebankingapp.model.repositories.CreditCardAccountRepository;
import com.example.onlinebankingapp.model.repositories.CustomerRepository;
import com.example.onlinebankingapp.model.repositories.SavingAccountRepository;
import com.example.onlinebankingapp.model.requests.CreditCardAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CreditCardAccountService {

    private final CreditCardAccountRepository creditCardAccountRepository;
    private final BranchRepository branchRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public CreditCardAccountService(CreditCardAccountRepository creditCardAccountRepository,
                                    BranchRepository branchRepository,
                                    CustomerRepository customerRepository) {
        this.creditCardAccountRepository = creditCardAccountRepository;
        this.branchRepository = branchRepository;
        this.customerRepository = customerRepository;
    }

    public List<CreditCardAccount> listAllAccounts() {
        return creditCardAccountRepository.findAll();
    }

    public CreditCardAccount getAccountById(Long accountId) {
        return creditCardAccountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("No Credit Card Account with id: " + accountId));
    }

    public CreditCardAccount insertAccount(CreditCardAccountRequest accountRequest) {

        Customer customer = customerRepository.findById(accountRequest.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("No Customer with id: " + accountRequest.getCustomerId()));
        Branch branch = branchRepository.findById(accountRequest.getBranchId())
                .orElseThrow(() -> new NoSuchElementException("No Branch with id: " + accountRequest.getBranchId()));

        CreditCardAccount newAccount = new CreditCardAccount();

        newAccount.setAccountNumber(accountRequest.getAccountNumber());
        newAccount.setBranch(branch);
        newAccount.setCustomer(customer);
        newAccount.setBalance(accountRequest.getBalance());
        newAccount.setInterestRate(accountRequest.getInterestRate());
        newAccount.setCreditLimit(accountRequest.getCreditLimit());

        return creditCardAccountRepository.save(newAccount);

    }

    public CreditCardAccount updateAccount(Long accountId, CreditCardAccountRequest accountRequest) {
        CreditCardAccount accountToUpdate = creditCardAccountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("No Credit Card Account with id: " + accountId));
        Customer customer = customerRepository.findById(accountRequest.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("No Customer with id: " + accountRequest.getCustomerId()));
        Branch branch = branchRepository.findById(accountRequest.getBranchId())
                .orElseThrow(() -> new NoSuchElementException("No Branch with id: " + accountRequest.getBranchId()));

        accountToUpdate.setBalance(accountRequest.getBalance());
        accountToUpdate.setAccountNumber(accountRequest.getAccountNumber());
        accountToUpdate.setCustomer(customer);
        accountToUpdate.setBranch(branch);
        accountToUpdate.setInterestRate(accountRequest.getInterestRate());
        accountToUpdate.setCreditLimit(accountRequest.getCreditLimit());

        return creditCardAccountRepository.save(accountToUpdate);

    }

    public void deleteAccount(Long accountId) {
        CreditCardAccount account = creditCardAccountRepository.findById(accountId)
                        .orElseThrow(() -> new NoSuchElementException("No Credit Card Account with id: " + accountId));
        creditCardAccountRepository.delete(account);
    }

}
