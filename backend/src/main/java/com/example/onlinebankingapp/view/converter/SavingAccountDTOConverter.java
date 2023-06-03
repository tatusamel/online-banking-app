package com.example.onlinebankingapp.view.converter;

import com.example.onlinebankingapp.model.entities.SavingAccount;
import com.example.onlinebankingapp.view.dto.SavingAccountDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SavingAccountDTOConverter {

    @Autowired
    ModelMapper modelMapper;

    public SavingAccountDTO convertToDto(SavingAccount savingAccount) {
        SavingAccountDTO savingAccountDTO = modelMapper.map(savingAccount, SavingAccountDTO.class);
        return savingAccountDTO;
    }

    public SavingAccount convertToDto(SavingAccountDTO savingAccountDTO) {
        SavingAccount savingAccount = modelMapper.map(savingAccountDTO, SavingAccount.class);
        return savingAccount;
    }
}
