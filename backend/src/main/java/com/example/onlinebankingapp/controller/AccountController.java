package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.requests.AccountRequest;
import com.example.onlinebankingapp.service.AccountService;
import java.util.List;
import java.util.stream.Collectors;

import com.example.onlinebankingapp.view.converter.AccountDTOConverter;
import com.example.onlinebankingapp.view.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountDTOConverter accountDTOConverter;
    @Autowired
    public AccountController(AccountService accountService,
                             AccountDTOConverter accountDTOConverter) {
        this.accountService = accountService;
        this.accountDTOConverter = accountDTOConverter;
    }

    @GetMapping()
    public ResponseEntity<List<AccountDTO>> getAll() {
        List<AccountDTO> accountDTOS = accountService.listAllAccounts()
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
}
