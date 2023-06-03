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
    private final BranchService branchService;
    private final CustomerService customerService;

    @Autowired
    public CheckingAccountService( CheckingAccountRepository checkingAccountRepository,
                                   BranchService branchService,
                                   CustomerService customerService) {
        this.branchService = branchService;
        this.checkingAccountRepository = checkingAccountRepository;
        this.customerService = customerService;
    }

    public List<CheckingAccount> listAllAccounts(){
        return checkingAccountRepository.findAll();
    }

    public CheckingAccount getAccountById(Long accountId) {
        return checkingAccountRepository.findById(accountId)
                .orElseThrow( () -> new NoSuchElementException("No Checking Account with id: " + accountId));
    }

    public CheckingAccount insertAccount(CheckingAccountRequest checkingAccountRequest) {

        CheckingAccount checkingAccount = new CheckingAccount();
        mapRequestToCheckingAccount(checkingAccountRequest, checkingAccount);
        return checkingAccountRepository.save(checkingAccount);
    }

    public CheckingAccount updateAccount(Long accountId,CheckingAccountRequest checkingAccountRequest) {


        CheckingAccount checkingAccount = this.getAccountById(accountId);
        mapRequestToCheckingAccount(checkingAccountRequest, checkingAccount);
        return checkingAccountRepository.save(checkingAccount);

    }

    public void deleteAccount(Long accountId) {
        CheckingAccount accountToDelete = this.getAccountById(accountId);
        checkingAccountRepository.delete(accountToDelete);
    }

    public CheckingAccount mapRequestToCheckingAccount(CheckingAccountRequest request, CheckingAccount checkingAccount) {
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        Branch branch = branchService.getBranchById(request.getBranchId());

        checkingAccount.setAccountNumber(request.getAccountNumber());
        checkingAccount.setBranch(branch);
        checkingAccount.setCustomer(customer);
        checkingAccount.setBalance(request.getBalance());
        return checkingAccount;
    }


}
