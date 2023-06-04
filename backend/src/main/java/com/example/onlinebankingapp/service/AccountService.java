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
    private final UserActionService userActionService;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          BranchService branchService,
                          CustomerService customerService,
                          AccountDTOConverter accountDTOConverter,
                          UserActionService userActionService)
    {
        this.accountRepository = accountRepository;
        this.branchService = branchService;
        this.customerService = customerService;
        this.accountDTOConverter = accountDTOConverter;
        this.userActionService = userActionService;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow( () -> new NoSuchElementException("No account with id: " + accountId));
    }

    public Account insertAccount(AccountRequest accountRequest) {

        Account newAccount = new Account();
        mapRequestToAccount(accountRequest, newAccount);
        userActionService.accountCreatedAction(accountRequest.getCustomerId(),
                accountRequest.getAccountNumber());
        return accountRepository.save(newAccount);

    }

    public Account updateAccount(Long accountId, AccountRequest accountRequest) {
        Account accountToUpdate = this.getAccountById(accountId);
        mapRequestToAccount(accountRequest, accountToUpdate);
        userActionService.accountUpdatedAction(accountRequest.getCustomerId(), accountRequest.getAccountNumber());
        return accountRepository.save(accountToUpdate);

    }

    public void deleteAccount(Long accountId) {
        Account account = this.getAccountById(accountId);
        userActionService.accountDeletedAction(account.getCustomer().getId(), account.getAccountNumber());
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

    public List<Account> getAccountsByBranchId(Long branchId) {
        return accountRepository.findAccountsByBranchId(branchId);
    }

    public Account mapRequestToAccount(AccountRequest request, Account account) {
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        Branch branch = branchService.getBranchById(request.getBranchId());

        account.setAccountNumber(request.getAccountNumber());
        account.setBranch(branch);
        account.setCustomer(customer);
        account.setBalance(request.getBalance());
        account.setAccountType(AccountType.valueOf(request.getAccountType()));

        return account;
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
}
