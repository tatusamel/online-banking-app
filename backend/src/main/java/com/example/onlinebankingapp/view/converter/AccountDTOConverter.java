package com.example.onlinebankingapp.view.converter;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.view.dto.AccountDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDTOConverter {

    @Autowired
    ModelMapper modelMapper;

    public AccountDTO convertToDto(Account account) {
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        return accountDTO;
    }

    public Account convertToEntity(AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);
        return account;
    }

}
