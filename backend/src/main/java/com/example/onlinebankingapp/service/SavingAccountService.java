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
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SavingAccountService {

    private final SavingAccountRepository savingAccountRepository;
    private final BranchService branchService;
    private final CustomerService customerService;

    @Autowired
    public SavingAccountService(SavingAccountRepository savingAccountRepository,
                                BranchService branchService,
                                CustomerService customerService)
    {
        this.savingAccountRepository = savingAccountRepository;
        this.branchService = branchService;
        this.customerService = customerService;
    }

    public List<SavingAccount> listAllAccounts() {
        return savingAccountRepository.findAll();
    }

    public SavingAccount getAccountById(Long accountId) {
        return savingAccountRepository.findById(accountId)
                .orElseThrow( () -> new NoSuchElementException("No Saving Account with id:" + accountId ) );
    }

    public SavingAccount insertAccount(SavingAccountRequest accountRequest) {

        SavingAccount newAccount = new SavingAccount();
        mapRequestToSavingAccount(newAccount, accountRequest);
        return savingAccountRepository.save(newAccount);

    }

    public SavingAccount updateAccount(Long accountId, SavingAccountRequest accountRequest) {

        SavingAccount accountToUpdate = this.getAccountById(accountId);
        mapRequestToSavingAccount(accountToUpdate, accountRequest);
        return savingAccountRepository.save(accountToUpdate);

    }

    public void deleteAccount(Long accountId) {

        SavingAccount accountToDelete = this.getAccountById(accountId);
        savingAccountRepository.delete(accountToDelete);
    }

    public SavingAccount mapRequestToSavingAccount(SavingAccount savingAccount, SavingAccountRequest accountRequest) {

        Customer customer = customerService.getCustomerById(accountRequest.getCustomerId());
        Branch branch = branchService.getBranchById(accountRequest.getBranchId());
        savingAccount.setBalance(accountRequest.getBalance());
        savingAccount.setAccountNumber(accountRequest.getAccountNumber());
        savingAccount.setCustomer(customer);
        savingAccount.setBranch(branch);
        savingAccount.setInterestRate(accountRequest.getInterestRate());
        if ( !accountRequest.getAccountType().equals(AccountType.SAVING_ACCOUNT.toString())) {
            throw new IllegalArgumentException("Account type must be SAVING_ACCOUNT");
        }
        savingAccount.setAccountType(AccountType.SAVING_ACCOUNT);
        return savingAccount;
    }
}
