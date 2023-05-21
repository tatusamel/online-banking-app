package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.repositories.AccountRepository;
import com.example.onlinebankingapp.model.repositories.BranchRepository;
import com.example.onlinebankingapp.model.repositories.CustomerRepository;
import com.example.onlinebankingapp.model.requests.AccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          BranchRepository branchRepository,
                          CustomerRepository customerRepository)
    {
        this.accountRepository = accountRepository;
        this.branchRepository = branchRepository;
        this.customerRepository = customerRepository;
    }

    public List<Account> listAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow( () -> new NoSuchElementException("No account with id: " + accountId));
    }

    public Account insertAccount(AccountRequest accountRequest) {
        Customer customer = customerRepository.findById(accountRequest.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("No Customer with id: " + accountRequest.getCustomerId()));
        Branch branch = branchRepository.findById(accountRequest.getBranchId())
                .orElseThrow(() -> new NoSuchElementException("No Branch with id: " + accountRequest.getBranchId()));

        Account newAccount = new Account();
        newAccount.setAccountNumber(accountRequest.getAccountNumber());
        newAccount.setBranch(branch);
        newAccount.setCustomer(customer);
        newAccount.setBalance(accountRequest.getBalance());

        return accountRepository.save(newAccount);

    }

    public Account updateAccount(Long accountId, AccountRequest accountRequest) {
        Account accountToUpdate = accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("No Account with id: " + accountId));
        Customer customer = customerRepository.findById(accountRequest.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("No Customer with id: " + accountRequest.getCustomerId()));
        Branch branch = branchRepository.findById(accountRequest.getBranchId())
                .orElseThrow(() -> new NoSuchElementException("No Branch with id: " + accountRequest.getBranchId()));

        accountToUpdate.setBalance(accountRequest.getBalance());
        accountToUpdate.setAccountNumber(accountRequest.getAccountNumber());
        accountToUpdate.setCustomer(customer);
        accountToUpdate.setBranch(branch);

        return accountRepository.save(accountToUpdate);

    }

    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("No Account with id: "+ accountId));
        accountRepository.delete(account);
    }

}
