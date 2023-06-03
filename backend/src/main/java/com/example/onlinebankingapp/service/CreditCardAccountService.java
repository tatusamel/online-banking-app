package com.example.onlinebankingapp.service;


import com.example.onlinebankingapp.model.entities.Branch;
import com.example.onlinebankingapp.model.entities.CreditCardAccount;
import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.model.enums.AccountType;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CreditCardAccountService {

    private final CreditCardAccountRepository creditCardAccountRepository;
    private final BranchService branchService;

    private final CustomerService customerService;

    @Autowired
    public CreditCardAccountService(CreditCardAccountRepository creditCardAccountRepository,
                                    BranchService branchService,
                                    CustomerService customerService) {
        this.creditCardAccountRepository = creditCardAccountRepository;
        this.branchService = branchService;
        this.customerService = customerService;
    }

    public List<CreditCardAccount> listAllAccounts() {
        return creditCardAccountRepository.findAll();
    }

    public CreditCardAccount getAccountById(Long accountId) {
        return creditCardAccountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("No Credit Card Account with id: " + accountId));
    }

    public CreditCardAccount insertAccount(CreditCardAccountRequest accountRequest) {

        CreditCardAccount newAccount = new CreditCardAccount();
        mapRequestToCreditCardAccount(accountRequest, newAccount);
        return creditCardAccountRepository.save(newAccount);

    }

    public CreditCardAccount updateAccount(Long accountId, CreditCardAccountRequest accountRequest) {
        CreditCardAccount accountToUpdate = this.getAccountById(accountId);
        mapRequestToCreditCardAccount(accountRequest, accountToUpdate);
        return creditCardAccountRepository.save(accountToUpdate);

    }

    public void deleteAccount(Long accountId) {
        CreditCardAccount account = this.getAccountById(accountId);
        creditCardAccountRepository.delete(account);
    }

    public CreditCardAccount mapRequestToCreditCardAccount(CreditCardAccountRequest request, CreditCardAccount creditCardAccount) {

        Customer customer = customerService.getCustomerById(request.getCustomerId());
        Branch branch = branchService.getBranchById(request.getBranchId());

        creditCardAccount.setBalance(request.getBalance());
        creditCardAccount.setAccountNumber(request.getAccountNumber());
        creditCardAccount.setCustomer(customer);
        creditCardAccount.setBranch(branch);
        creditCardAccount.setInterestRate(request.getInterestRate());
        creditCardAccount.setCreditLimit(request.getCreditLimit());
        if ( !request.getAccountType().equals(AccountType.CREDIT_CARD_ACCOUNT.toString())) {
            throw new IllegalArgumentException("Account type must be CREDIT_CARD_ACCOUNT");
        }
        creditCardAccount.setAccountType(AccountType.CREDIT_CARD_ACCOUNT);
        return creditCardAccount;
    }

}
