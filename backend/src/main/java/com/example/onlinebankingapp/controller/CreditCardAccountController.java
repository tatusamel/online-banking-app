package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.CreditCardAccount;
import com.example.onlinebankingapp.model.entities.SavingAccount;
import com.example.onlinebankingapp.model.requests.CreditCardAccountRequest;
import com.example.onlinebankingapp.model.requests.SavingAccountRequest;
import com.example.onlinebankingapp.service.CreditCardAccountService;
import com.example.onlinebankingapp.service.SavingAccountService;
import com.example.onlinebankingapp.service.UserActionService;
import com.example.onlinebankingapp.view.converter.CreditCardAccountDTOConverter;
import com.example.onlinebankingapp.view.dto.CreditCardAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/credit-card-accounts")
public class CreditCardAccountController {

    private final CreditCardAccountService creditCardAccountService;
    private final CreditCardAccountDTOConverter creditCardAccountDTOConverter;
    @Autowired
    public CreditCardAccountController(CreditCardAccountService creditCardAccountService,
                                       CreditCardAccountDTOConverter creditCardAccountDTOConverter) {
        this.creditCardAccountService = creditCardAccountService;
        this.creditCardAccountDTOConverter = creditCardAccountDTOConverter;
    }

    @GetMapping
    public ResponseEntity<List<CreditCardAccountDTO>> getAll(){
        List<CreditCardAccountDTO> accountDTOS = creditCardAccountService.getAll()
                .stream().map(creditCardAccountDTOConverter::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(accountDTOS, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<CreditCardAccountDTO> getAccountById(@PathVariable Long accountId) {
        CreditCardAccountDTO creditCardAccountDTO = creditCardAccountDTOConverter.convertToDto(creditCardAccountService.getAccountById(accountId));
        return new ResponseEntity<>(creditCardAccountDTO, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<CreditCardAccountDTO> insertAccount(@RequestBody CreditCardAccountRequest request) {
        CreditCardAccountDTO creditCardAccountDTO = creditCardAccountDTOConverter.convertToDto(creditCardAccountService.insertAccount(request));
        return new ResponseEntity<>(creditCardAccountDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<CreditCardAccountDTO> updateAccount(@PathVariable Long accountId, @RequestBody CreditCardAccountRequest request) {
        CreditCardAccountDTO creditCardAccountDTO =  creditCardAccountDTOConverter.convertToDto(creditCardAccountService.updateAccount(accountId,request));
        return new ResponseEntity<>(creditCardAccountDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        creditCardAccountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
