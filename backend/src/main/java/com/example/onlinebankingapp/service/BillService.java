package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.entities.Bill;
import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.model.repositories.AccountRepository;
import com.example.onlinebankingapp.model.repositories.BillRepository;
import com.example.onlinebankingapp.model.repositories.CustomerRepository;
import com.example.onlinebankingapp.model.requests.AccountRequest;
import com.example.onlinebankingapp.model.requests.BillRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public BillService(BillRepository billRepository, AccountRepository accountRepository) {
        this.billRepository = billRepository;
        this.accountRepository = accountRepository;
    }

    public List<Bill> listAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new NoSuchElementException("No Bill with id: " + billId));
    }

    public Bill insertBill(BillRequest billRequest) {
        Account account = accountRepository.findById(billRequest.getAccountId())
                .orElseThrow(() -> new NoSuchElementException("No Account with id: " + billRequest.getAccountId()));

        Bill bill = new Bill();
        bill.setName(billRequest.getName());
        bill.setAmount(billRequest.getAmount());
        bill.setDueDate(billRequest.getDueDate());
        bill.setAccount(account);

        return billRepository.save(bill);
    }

    public Bill updateBill(Long billId, BillRequest billRequest) {
        Bill billToUpdate = billRepository.findById(billId)
                .orElseThrow(() -> new NoSuchElementException("No Bill with id: " + billId));
        Account account = accountRepository.findById(billRequest.getAccountId())
                .orElseThrow(() -> new NoSuchElementException("No Account with id: " + billRequest.getAccountId()));

        billToUpdate.setName(billRequest.getName());
        billToUpdate.setAmount(billRequest.getAmount());
        billToUpdate.setDueDate(billRequest.getDueDate());
        billToUpdate.setAccount(account);

        return billRepository.save(billToUpdate);
    }

    public void deleteBill(Long billId) {
        Bill billToDelete = billRepository.findById(billId)
                .orElseThrow(() -> new NoSuchElementException("No Bill with id: " + billId));
        billRepository.delete(billToDelete);
    }



}
