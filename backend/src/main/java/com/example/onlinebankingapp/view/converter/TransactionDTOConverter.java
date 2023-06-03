package com.example.onlinebankingapp.view.converter;

import com.example.onlinebankingapp.model.entities.Transaction;
import com.example.onlinebankingapp.view.dto.TransactionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionDTOConverter {

    @Autowired
    ModelMapper modelMapper;

    public TransactionDTO convertToDto(Transaction transaction )  {
        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);
        return transactionDTO;
    }

    public Transaction convertToEntity(TransactionDTO transactionDTO) {
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
        return transaction;
    }
}
