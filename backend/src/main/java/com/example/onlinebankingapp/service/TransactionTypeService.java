package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.TransactionType;
import com.example.onlinebankingapp.model.repositories.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionTypeService {

    private final TransactionTypeRepository transactionTypeRepository;

    @Autowired
    public TransactionTypeService(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public List<TransactionType> listAllTransactionTypes() {
        return transactionTypeRepository.findAll();
    }

    public TransactionType getTransactionTypeById(Long transactionTypeId) {
        return transactionTypeRepository.findById(transactionTypeId)
                .orElseThrow(() -> new NoSuchElementException("No TransactionType with id: " + transactionTypeId));
    }

    public TransactionType insertTransactionType(TransactionType transactionType) {
        return transactionTypeRepository.save(transactionType);
    }

    public TransactionType updateTransactionType(Long transactionTypeId, TransactionType transactionType) {
        TransactionType transactionTypeToUpdate = transactionTypeRepository.findById(transactionTypeId)
                .orElseThrow(() -> new NoSuchElementException("No TransactionType with id: " + transactionTypeId));

        transactionTypeToUpdate.setName(transactionType.getName());

        return transactionTypeRepository.save(transactionTypeToUpdate);
    }

    public void deleteTransactionType(Long transactionTypeId) {
        TransactionType transactionType = transactionTypeRepository.findById(transactionTypeId)
                .orElseThrow(() -> new NoSuchElementException("No TransactionType with id: " + transactionTypeId));
        transactionTypeRepository.delete(transactionType);
    }
}
