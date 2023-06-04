package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.requests.AccountRequest;
import com.example.onlinebankingapp.service.AccountService;
import java.util.List;
import java.util.stream.Collectors;

import com.example.onlinebankingapp.service.BillService;
import com.example.onlinebankingapp.service.TransactionService;
import com.example.onlinebankingapp.view.converter.AccountDTOConverter;
import com.example.onlinebankingapp.view.dto.AccountDTO;
import com.example.onlinebankingapp.view.dto.BillDTO;
import com.example.onlinebankingapp.view.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountDTOConverter accountDTOConverter;
    private final TransactionService transactionService;
    private final BillService billService;


    @Autowired
    public AccountController(AccountService accountService,
                             AccountDTOConverter accountDTOConverter,
                             TransactionService transactionService,
                             BillService billService) {
        this.accountService = accountService;
        this.accountDTOConverter = accountDTOConverter;
        this.transactionService = transactionService;
        this.billService = billService;
    }

    @GetMapping()
    public ResponseEntity<List<AccountDTO>> getAll() {
        List<AccountDTO> accountDTOS = accountService.getAll()
                .stream().map(accountDTOConverter::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(accountDTOS, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long accountId) {
        AccountDTO accountDTO = accountDTOConverter.convertToDto(accountService.getAccountById(accountId));
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<AccountDTO> insertAccount(@RequestBody AccountRequest accountRequest) {
        AccountDTO accountDTO = accountDTOConverter.convertToDto(accountService.insertAccount(accountRequest));
        return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long accountId, @RequestBody AccountRequest accountRequest) {
        AccountDTO accountDTO =  accountDTOConverter.convertToDto(accountService.updateAccount(accountId,accountRequest));
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountNumber(@PathVariable String accountNumber ) {
        List<TransactionDTO> transactionDTOS = transactionService.getAllTransactionsByAccountNumber(accountNumber);
        return new ResponseEntity<>(transactionDTOS, HttpStatus.OK);
    }

    @GetMapping("/{accountNumber}/bills/UNPAID")
    public List<BillDTO> getUnpaidBillsByAccountId(@PathVariable String accountNumber) {
        return billService.getUnpaidBillsByAccountId(accountNumber);
    }

    @GetMapping("/{accountNumber}/bills/PAID")
    public List<BillDTO> getPaidBillsByAccountId(@PathVariable String accountNumber) {
        return billService.getPaidBillsByAccountId(accountNumber);
    }

    @GetMapping("total-balance")
    public ResponseEntity<Double> getTotalBalance() {
        return new ResponseEntity<>(accountService.getTotalBalance(), HttpStatus.OK);
    }

    @GetMapping("total-balance/{customerId}")
    public ResponseEntity<Double> getTotalBalanceByCustomerId(@PathVariable Long customerId) {
        return new ResponseEntity<>(accountService.getTotalBalanceByCustomerId(customerId), HttpStatus.OK);
    }

    @GetMapping("average-balance/{accountType}")
    public ResponseEntity<Double> getAvgBalanceByAccountType(@PathVariable String accountType) {
        return new ResponseEntity<> (accountService.getAvgBalanceByAccountType(accountType), HttpStatus.OK);
    }

    @GetMapping("/number-of-accounts/{accountType}")
    public ResponseEntity<Integer> getNumberOfAccountsByAccountType(@PathVariable String accountType) {
        return new ResponseEntity<>(accountService.getNumberOfAccountsByAccountType(accountType), HttpStatus.OK);
    }

    @GetMapping("/max-balance")
    public ResponseEntity<Integer> getMaxBalance() {
        return new ResponseEntity<>(accountService.getMaxBalance(), HttpStatus.OK);
    }


    @GetMapping("/by-name/{accountName}")
    public AccountDTO getAccountByName(@PathVariable String accountName) {
        return accountDTOConverter.convertToDto(accountService.getAccountByAccountNumber(accountName));
    }
}
