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
import java.util.Optional;

@Service
public class CreditCardAccountService {

    private CreditCardAccountRepository creditCardAccountRepository;
    private BranchRepository branchRepository;

    private CustomerRepository customerRepository;

    @Autowired
    public CreditCardAccountService(CreditCardAccountRepository creditCardAccountRepository,
                                    BranchRepository branchRepository,
                                    CustomerRepository customerRepository) {
        this.creditCardAccountRepository = creditCardAccountRepository;
        this.branchRepository = branchRepository;
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<List<CreditCardAccount>> listAllAccounts() {
        return new ResponseEntity<>(creditCardAccountRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<CreditCardAccount> getAccountById(Long accountId) {
        Optional<CreditCardAccount> account = creditCardAccountRepository.findById(accountId);
        if ( account.isEmpty() ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    public ResponseEntity<CreditCardAccount> insertAccount(CreditCardAccountRequest accountRequest) {

        Long customerId = accountRequest.getCustomerId();
        Long branchId = accountRequest.getBranchId();

        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Branch> branch = branchRepository.findById(branchId);

        if ( customer.isEmpty() || branch.isEmpty() ) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        CreditCardAccount newAccount = new CreditCardAccount();

        newAccount.setAccountNumber(accountRequest.getAccountNumber());
        newAccount.setBranch(branch.get());
        newAccount.setCustomer(customer.get());
        newAccount.setBalance(accountRequest.getBalance());
        newAccount.setInterestRate(accountRequest.getInterestRate());
        newAccount.setCreditLimit(accountRequest.getCreditLimit());

        return new ResponseEntity<>(creditCardAccountRepository.save(newAccount),HttpStatus.CREATED);

    }

    public ResponseEntity<CreditCardAccount> updateAccount(Long accountId, CreditCardAccountRequest accountRequest) {
        Optional<CreditCardAccount> account = creditCardAccountRepository.findById(accountId);
        Optional<Customer> customer = customerRepository.findById(accountRequest.getCustomerId());
        Optional<Branch> branch = branchRepository.findById(accountRequest.getBranchId());

        if ( account.isEmpty() || customer.isEmpty() || branch.isEmpty() ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        CreditCardAccount accountToUpdate = account.get();

        accountToUpdate.setBalance(accountRequest.getBalance());
        accountToUpdate.setAccountNumber(accountRequest.getAccountNumber());
        accountToUpdate.setCustomer(customer.get());
        accountToUpdate.setBranch(branch.get());
        accountToUpdate.setInterestRate(accountRequest.getInterestRate());
        accountToUpdate.setCreditLimit(accountRequest.getCreditLimit());

        return new ResponseEntity<>(creditCardAccountRepository.save(accountToUpdate), HttpStatus.OK);

    }

    public ResponseEntity<Void> deleteAccount(Long accountId) {
        Optional<CreditCardAccount> account = creditCardAccountRepository.findById(accountId);
        if ( account.isEmpty() ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        creditCardAccountRepository.delete(account.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
