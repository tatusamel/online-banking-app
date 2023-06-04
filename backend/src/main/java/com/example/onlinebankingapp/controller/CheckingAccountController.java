package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.CheckingAccount;
import com.example.onlinebankingapp.model.requests.CheckingAccountRequest;
import com.example.onlinebankingapp.service.CheckingAccountService;
import com.example.onlinebankingapp.view.converter.CheckingAccountDTOConverter;
import com.example.onlinebankingapp.view.dto.CheckingAccountDTO;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/checking-accounts")
public class CheckingAccountController {


    private final CheckingAccountService checkingAccountService;
    private final CheckingAccountDTOConverter checkingAccountDTOConverter;

    @Autowired
    public CheckingAccountController(CheckingAccountService checkingAccountService,
                                     CheckingAccountDTOConverter checkingAccountDTOConverter) {
        this.checkingAccountService = checkingAccountService;
        this.checkingAccountDTOConverter = checkingAccountDTOConverter;
    }

    @GetMapping
    public ResponseEntity<List<CheckingAccountDTO>> getAll() {
        List<CheckingAccountDTO> accounts = checkingAccountService.getAll()
                .stream().map(checkingAccountDTOConverter::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<CheckingAccountDTO> getAccountById(@PathVariable Long accountId) {
        CheckingAccountDTO accountDTO = checkingAccountDTOConverter.convertToDto(checkingAccountService.getAccountById(accountId));
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<CheckingAccountDTO> insertAccount(@RequestBody CheckingAccountRequest checkingAccountRequest) {
        CheckingAccountDTO accountDTO = checkingAccountDTOConverter.convertToDto(checkingAccountService.insertAccount(checkingAccountRequest));
        return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<CheckingAccountDTO> updateAccount(@PathVariable Long accountId, @RequestBody CheckingAccountRequest checkingAccountRequest) {
        CheckingAccountDTO accountDTO = checkingAccountDTOConverter.convertToDto(checkingAccountService.updateAccount(accountId, checkingAccountRequest));
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        checkingAccountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
