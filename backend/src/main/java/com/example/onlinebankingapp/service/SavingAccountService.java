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
    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public SavingAccountService(SavingAccountRepository savingAccountRepository,
                                BranchRepository branchRepository,
                                CustomerRepository customerRepository)
    {
        this.savingAccountRepository = savingAccountRepository;
        this.branchRepository = branchRepository;
        this.customerRepository = customerRepository;
    }

    public List<SavingAccount> listAllAccounts() {
        return savingAccountRepository.findAll();
    }

    public SavingAccount getAccountById(Long accountId) {
        return savingAccountRepository.findById(accountId)
                .orElseThrow( () -> new NoSuchElementException("No Saving Account with id:" + accountId ) );
    }

    public SavingAccount insertAccount(SavingAccountRequest accountRequest) {

        Customer customer = customerRepository.findById(accountRequest.getCustomerId())
                .orElseThrow( () -> new NoSuchElementException("No customer with id:" + accountRequest.getCustomerId()));

        Branch branch = branchRepository.findById(accountRequest.getBranchId())
                .orElseThrow( () -> new NoSuchElementException("No branch with id:" + accountRequest.getBranchId()));

        SavingAccount newAccount = new SavingAccount();

        newAccount.setAccountNumber(accountRequest.getAccountNumber());
        newAccount.setBranch(branch);
        newAccount.setCustomer(customer);
        newAccount.setBalance(accountRequest.getBalance());
        newAccount.setInterestRate(accountRequest.getInterestRate());

        return savingAccountRepository.save(newAccount);

    }

    public SavingAccount updateAccount(Long accountId, SavingAccountRequest accountRequest) {

        SavingAccount accountToUpdate = savingAccountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("No saving account with id: " + accountId));

        Customer customer = customerRepository.findById(accountRequest.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("No customer with id: " + accountRequest.getCustomerId()));

        Branch branch = branchRepository.findById(accountRequest.getBranchId())
                .orElseThrow( () -> new NoSuchElementException("No branch with id: " + accountRequest.getBranchId()));

        accountToUpdate.setBalance(accountRequest.getBalance());
        accountToUpdate.setAccountNumber(accountRequest.getAccountNumber());
        accountToUpdate.setCustomer(customer);
        accountToUpdate.setBranch(branch);
        accountToUpdate.setInterestRate(accountRequest.getInterestRate());

        return savingAccountRepository.save(accountToUpdate);

    }

    public void deleteAccount(Long accountId) {

        SavingAccount accountToDelete = savingAccountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("No Saving account with id: " + accountId ));
        savingAccountRepository.delete(accountToDelete);
    }

}
