package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.enums.AccountType;
import com.example.onlinebankingapp.model.repositories.AccountRepository;
import com.example.onlinebankingapp.model.requests.AccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BranchService branchService;
    private final CustomerService customerService;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          BranchService branchService,
                          CustomerService customerService)
    {
        this.accountRepository = accountRepository;
        this.branchService = branchService;
        this.customerService = customerService;
    }

    public List<Account> listAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow( () -> new NoSuchElementException("No account with id: " + accountId));
    }

    public Account insertAccount(AccountRequest accountRequest) {
        Customer customer = customerService.getCustomerById(accountRequest.getCustomerId());
        Branch branch = branchService.getBranchById(accountRequest.getBranchId());

        Account newAccount = new Account();
        newAccount.setAccountNumber(accountRequest.getAccountNumber());
        newAccount.setBranch(branch);
        newAccount.setCustomer(customer);
        newAccount.setBalance(accountRequest.getBalance());
        newAccount.setAccountType(AccountType.valueOf(accountRequest.getAccountType()));

        return accountRepository.save(newAccount);

    }

    public Account updateAccount(Long accountId, AccountRequest accountRequest) {
        Account accountToUpdate = this.getAccountById(accountId);
        Customer customer = customerService.getCustomerById(accountRequest.getCustomerId());
        Branch branch = branchService.getBranchById(accountRequest.getBranchId());

        accountToUpdate.setBalance(accountRequest.getBalance());
        accountToUpdate.setAccountNumber(accountRequest.getAccountNumber());
        accountToUpdate.setCustomer(customer);
        accountToUpdate.setBranch(branch);
        accountToUpdate.setAccountType(AccountType.valueOf(accountRequest.getAccountType()));

        return accountRepository.save(accountToUpdate);

    }

    public void deleteAccount(Long accountId) {
        Account account = this.getAccountById(accountId);
        accountRepository.delete(account);
    }

}
