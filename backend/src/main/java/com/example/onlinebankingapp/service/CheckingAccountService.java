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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheckingAccountService {

    private CheckingAccountRepository checkingAccountRepository;
    private BranchRepository branchRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public CheckingAccountService( CheckingAccountRepository checkingAccountRepository,
                                   BranchRepository branchRepository,
                                   CustomerRepository customerRepository) {
        this.branchRepository = branchRepository;
        this.checkingAccountRepository = checkingAccountRepository;
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<List<CheckingAccount>> listAllAccounts(){
        return new ResponseEntity<>(checkingAccountRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<CheckingAccount> getAccountById(Long accountId) {
        Optional<CheckingAccount> account = checkingAccountRepository.findById(accountId);
        if ( account.isEmpty() ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    public ResponseEntity<CheckingAccount> insertAccount(CheckingAccountRequest checkingAccountRequest) {
        Long customerId = checkingAccountRequest.getCustomerId();
        Long branchId = checkingAccountRequest.getBranchId();

        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Branch> branch = branchRepository.findById(branchId);

        if ( customer.isEmpty() || branch.isEmpty() ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        CheckingAccount newAccount = new CheckingAccount();
        newAccount.setAccountNumber(checkingAccountRequest.getAccountNumber());
        newAccount.setBranch(branch.get());
        newAccount.setCustomer(customer.get());
        newAccount.setBalance(checkingAccountRequest.getBalance());

        return new ResponseEntity(checkingAccountRepository.save(newAccount), HttpStatus.CREATED);
    }

    public ResponseEntity<CheckingAccount> updateAccount(Long accountId,CheckingAccountRequest checkingAccountRequest) {
        Long customerId = checkingAccountRequest.getCustomerId();
        Long branchId = checkingAccountRequest.getBranchId();

        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Branch> branch = branchRepository.findById(branchId);
        Optional<CheckingAccount> account = checkingAccountRepository.findById(accountId);

        if ( customer.isEmpty() || branch.isEmpty() || account.isEmpty() ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        CheckingAccount newAccount = new CheckingAccount();
        newAccount.setBalance(checkingAccountRequest.getBalance());
        newAccount.setCustomer(customer.get());
        newAccount.setBranch(branch.get());
        newAccount.setAccountNumber(checkingAccountRequest.getAccountNumber());

        return new ResponseEntity<>(checkingAccountRepository.save(newAccount), HttpStatus.CREATED);

    }

    public ResponseEntity<Void> deleteAccount(Long accountId) {
        Optional<CheckingAccount> account = checkingAccountRepository.findById(accountId);
        if ( account.isEmpty() ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        checkingAccountRepository.delete(account.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
