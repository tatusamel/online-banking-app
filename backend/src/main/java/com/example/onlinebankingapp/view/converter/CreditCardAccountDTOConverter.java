package com.example.onlinebankingapp.view.converter;

import com.example.onlinebankingapp.model.entities.CreditCardAccount;
import com.example.onlinebankingapp.view.dto.CreditCardAccountDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditCardAccountDTOConverter {

    @Autowired
    ModelMapper modelMapper;

    public CreditCardAccountDTO convertToDto(CreditCardAccount creditCardAccount) {
        CreditCardAccountDTO creditCardAccountDTO = modelMapper.map(creditCardAccount, CreditCardAccountDTO.class);
        return creditCardAccountDTO;
    }

    public CreditCardAccount convertToEntity(CreditCardAccountDTO creditCardAccountDTO) {
        CreditCardAccount creditCardAccount = modelMapper.map(creditCardAccountDTO, CreditCardAccount.class);
        return creditCardAccount;
    }

}
