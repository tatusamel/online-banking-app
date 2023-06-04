package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.entities.Transaction;
import com.example.onlinebankingapp.model.enums.TransactionType;
import com.example.onlinebankingapp.model.repositories.TransactionRepository;
import com.example.onlinebankingapp.model.requests.TransactionRequest;
import com.example.onlinebankingapp.view.converter.TransactionDTOConverter;
import com.example.onlinebankingapp.view.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionDTOConverter transactionDTOConverter;
    private final UserActionService userActionService;
    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountService accountService,
                              TransactionDTOConverter transactionDTOConverter,
                              UserActionService userActionService)
    {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionDTOConverter = transactionDTOConverter;
        this.userActionService = userActionService;
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("No Transaction with id: " + transactionId));
    }

    public Transaction createTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();
        mapRequestToTransaction(request, transaction);
        userActionService.transactionCreatedAction(transaction.getFromAccount().getCustomer().getId(), transaction);
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long transactionId, TransactionRequest request) {
        Transaction transaction = this.getTransactionById(transactionId);
        mapRequestToTransaction(request, transaction);
        userActionService.transactionUpdatedAction(transaction.getFromAccount().getCustomer().getId(), transaction);
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        Transaction transaction = this.getTransactionById(transactionId);
        userActionService.transactionDeletedAction(transaction.getFromAccount().getCustomer().getId(), transaction);
        transactionRepository.delete(transaction);
    }

    public List<TransactionDTO> getIncomingTransactionsByAccountNumber(String accountNumber) {
        Account account = accountService.getAccountByAccountNumber(accountNumber); // this will throw error if account does not exist
        List<Transaction> transactions = transactionRepository.findIncomingTransactionsByAccountNumber(accountNumber);
        return transactions.stream()
                .map(transactionDTOConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getOutgoingTransactionsByAccountNumber(String accountNumber) {
        Account account = accountService.getAccountByAccountNumber(accountNumber); // this will throw error if account does not exist
        List<Transaction> transactions = transactionRepository.findOutgoingTransactionsByAccountNumber(accountNumber);
        return transactions.stream()
                .map(transactionDTOConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getAllTransactionsByAccountNumber(String accountNumber) {
        Account account = accountService.getAccountByAccountNumber(accountNumber); // this will throw error if account does not exist
        // incoming transactions
        List<TransactionDTO> transactionDTOS = this.getIncomingTransactionsByAccountNumber(accountNumber);
        // outgoing transactions
        transactionDTOS.addAll(this.getOutgoingTransactionsByAccountNumber(accountNumber));

        return transactionDTOS;
    }

    public Transaction mapRequestToTransaction(TransactionRequest request, Transaction transaction) {
        Account fromAccount = accountService.getAccountById(request.getFromAccountId());
        Account toAccount = accountService.getAccountById(request.getToAccountId());

        if ( fromAccount.getBalance() < request.getAmount() ) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());
        toAccount.setBalance(toAccount.getBalance() + request.getAmount());
        accountService.saveAccount(fromAccount);
        accountService.saveAccount(toAccount);

        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(new Date());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);

        return transaction;
    }


    public Double getTotalDepositsWithinPeriod(Date startDate, Date endDate) {
        return transactionRepository.findTotalDepositsWithinPeriod(startDate, endDate);
    }

    public List<Transaction> getDepositsWithinPeriod(Date startDate, Date endDate) {
        return transactionRepository.findDepositsWithinPeriod(startDate, endDate);
    }
}
