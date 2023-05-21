package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.Branch;
import com.example.onlinebankingapp.model.entities.CheckingAccount;
import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.model.repositories.BranchRepository;
import com.example.onlinebankingapp.model.repositories.CheckingAccountRepository;
import com.example.onlinebankingapp.model.repositories.CustomerRepository;
import com.example.onlinebankingapp.model.requests.CheckingAccountRequest;
import com.example.onlinebankingapp.model.requests.CustomerRequest;
import org.apache.coyote.Response;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CheckingAccountService {

    private final CheckingAccountRepository checkingAccountRepository;
    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CheckingAccountService( CheckingAccountRepository checkingAccountRepository,
                                   BranchRepository branchRepository,
                                   CustomerRepository customerRepository) {
        this.branchRepository = branchRepository;
        this.checkingAccountRepository = checkingAccountRepository;
        this.customerRepository = customerRepository;
    }

    public List<CheckingAccount> listAllAccounts(){
        return checkingAccountRepository.findAll();
    }

    public CheckingAccount getAccountById(Long accountId) {
        return checkingAccountRepository.findById(accountId)
                .orElseThrow( () -> new NoSuchElementException("No Checking Account with id: " + accountId));
    }

    public CheckingAccount insertAccount(CheckingAccountRequest checkingAccountRequest) {

        Customer customer = customerRepository.findById(checkingAccountRequest.getCustomerId())
                .orElseThrow( () -> new NoSuchElementException("No customer with id: " + checkingAccountRequest.getCustomerId()));

        Branch branch = branchRepository.findById(checkingAccountRequest.getBranchId())
                .orElseThrow( () -> new NoSuchElementException("No branch with id: " + checkingAccountRequest.getBranchId()));

        CheckingAccount newAccount = new CheckingAccount();
        newAccount.setAccountNumber(checkingAccountRequest.getAccountNumber());
        newAccount.setBranch(branch);
        newAccount.setCustomer(customer);
        newAccount.setBalance(checkingAccountRequest.getBalance());

        return checkingAccountRepository.save(newAccount);
    }

    public CheckingAccount updateAccount(Long accountId,CheckingAccountRequest checkingAccountRequest) {


        CheckingAccount accountToUpdate = checkingAccountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("No Checking Account with id: " + accountId));
        Customer customer = customerRepository.findById(checkingAccountRequest.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("No Customer with id: " + checkingAccountRequest.getCustomerId()));
        Branch branch = branchRepository.findById(checkingAccountRequest.getBranchId())
                .orElseThrow(() -> new NoSuchElementException("No Branch with id: " + checkingAccountRequest.getBranchId()));

        accountToUpdate.setBalance(checkingAccountRequest.getBalance());
        accountToUpdate.setCustomer(customer);
        accountToUpdate.setBranch(branch);
        accountToUpdate.setAccountNumber(checkingAccountRequest.getAccountNumber());

        return checkingAccountRepository.save(accountToUpdate);

    }

    public void deleteAccount(Long accountId) {
        CheckingAccount accountToDelete = checkingAccountRepository.findById(accountId)
                .orElseThrow( () -> new NoSuchElementException("No Checking Account with id: " + accountId));
        checkingAccountRepository.delete(accountToDelete);
    }


}
