package com.example.onlinebankingapp.view.converter;

import com.example.onlinebankingapp.model.entities.Loan;
import com.example.onlinebankingapp.view.dto.LoanDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoanDTOConverter {

    @Autowired
    ModelMapper modelMapper;

    public LoanDTO convertToDto(Loan loan) {
        LoanDTO loanDTO = modelMapper.map(loan, LoanDTO.class);
        return loanDTO;
    }

    public Loan convertToEntity(LoanDTO loanDTO) {
        Loan loan = modelMapper.map(loanDTO, Loan.class);
        return loan;
    }
}
