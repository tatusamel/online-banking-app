package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.enums.AccountType;
import com.example.onlinebankingapp.model.repositories.AccountRepository;
import com.example.onlinebankingapp.model.requests.AccountRequest;
import com.example.onlinebankingapp.view.converter.AccountDTOConverter;
import com.example.onlinebankingapp.view.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BranchService branchService;
    private final CustomerService customerService;
    private final AccountDTOConverter accountDTOConverter;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          BranchService branchService,
                          CustomerService customerService,
                          AccountDTOConverter accountDTOConverter)
    {
        this.accountRepository = accountRepository;
        this.branchService = branchService;
        this.customerService = customerService;
        this.accountDTOConverter = accountDTOConverter;
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

    public List<AccountDTO> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findAccountsByCustomerId(customerId)
                .stream().map(accountDTOConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow( () -> new NoSuchElementException("No account with account number: " + accountNumber));
    }
}
