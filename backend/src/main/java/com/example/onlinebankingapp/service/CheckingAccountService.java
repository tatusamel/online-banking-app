package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.Branch;
import com.example.onlinebankingapp.model.entities.CheckingAccount;
import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.model.enums.AccountType;
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
    private final UserActionService userActionService;

    @Autowired
    public CheckingAccountService( CheckingAccountRepository checkingAccountRepository,
                                   BranchService branchService,
                                   CustomerService customerService,
                                   UserActionService userActionService ){
        this.branchService = branchService;
        this.checkingAccountRepository = checkingAccountRepository;
        this.customerService = customerService;
        this.userActionService = userActionService;
    }

    public List<CheckingAccount> getAll(){
        return checkingAccountRepository.findAll();
    }

    public CheckingAccount getAccountById(Long accountId) {
        return checkingAccountRepository.findById(accountId)
                .orElseThrow( () -> new NoSuchElementException("No Checking Account with id: " + accountId));
    }

    public CheckingAccount insertAccount(CheckingAccountRequest request) {

        CheckingAccount checkingAccount = new CheckingAccount();
        mapRequestToCheckingAccount(request, checkingAccount);
        userActionService.accountCreatedAction(request.getCustomerId(), request.getAccountNumber());
        return checkingAccountRepository.save(checkingAccount);
    }

    public CheckingAccount updateAccount(Long accountId,CheckingAccountRequest request) {


        CheckingAccount checkingAccount = this.getAccountById(accountId);
        mapRequestToCheckingAccount(request, checkingAccount);
        userActionService.accountUpdatedAction(request.getCustomerId(), request.getAccountNumber());
        return checkingAccountRepository.save(checkingAccount);

    }

    public void deleteAccount(Long accountId) {
        CheckingAccount accountToDelete = this.getAccountById(accountId);
        userActionService.accountDeletedAction(accountToDelete.getCustomer().getId(), accountToDelete.getAccountNumber());
        checkingAccountRepository.delete(accountToDelete);
    }

    public CheckingAccount mapRequestToCheckingAccount(CheckingAccountRequest request, CheckingAccount checkingAccount) {
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        Branch branch = branchService.getBranchById(request.getBranchId());

        checkingAccount.setAccountNumber(request.getAccountNumber());
        checkingAccount.setBranch(branch);
        checkingAccount.setCustomer(customer);
        checkingAccount.setBalance(request.getBalance());
        if ( !request.getAccountType().equals(AccountType.CHECKING_ACCOUNT.toString())) {
            throw new IllegalArgumentException("Account type must be CHECKING_ACCOUNT");
        }
        checkingAccount.setAccountType(AccountType.CHECKING_ACCOUNT);
        return checkingAccount;
    }


}
