package com.example.onlinebankingapp.view.converter;

import com.example.onlinebankingapp.model.entities.CheckingAccount;
import com.example.onlinebankingapp.view.dto.CheckingAccountDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckingAccountDTOConverter {

    @Autowired
    ModelMapper modelMapper;

    public CheckingAccountDTO convertToDto(CheckingAccount checkingAccount) {
        CheckingAccountDTO checkingAccountDTO = modelMapper.map(checkingAccount, CheckingAccountDTO.class);
        return checkingAccountDTO;
    }

    public CheckingAccount convertToEntity(CheckingAccountDTO checkingAccountDTO) {
        CheckingAccount checkingAccount = modelMapper.map(checkingAccountDTO, CheckingAccount.class);
        return checkingAccount;
    }
}
