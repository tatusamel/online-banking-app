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
    private final AccountService accountService;

    @Autowired
    public BillService(BillRepository billRepository, AccountService accountService) {
        this.billRepository = billRepository;
        this.accountService = accountService;
    }

    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    public Bill getBillById(Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new NoSuchElementException("No Bill with id: " + billId));
    }

    public Bill insertBill(BillRequest billRequest) {
        Account account = accountService.getAccountById(billRequest.getAccountId());

        Bill bill = new Bill();
        bill.setName(billRequest.getName());
        bill.setAmount(billRequest.getAmount());
        bill.setDueDate(billRequest.getDueDate());
        bill.setAccount(account);

        return billRepository.save(bill);
    }

    public Bill updateBill(Long billId, BillRequest billRequest) {
        Bill billToUpdate = this.getBillById(billId);
        Account account = accountService.getAccountById(billRequest.getAccountId());

        billToUpdate.setName(billRequest.getName());
        billToUpdate.setAmount(billRequest.getAmount());
        billToUpdate.setDueDate(billRequest.getDueDate());
        billToUpdate.setAccount(account);

        return billRepository.save(billToUpdate);
    }

    public void deleteBill(Long billId) {
        Bill billToDelete = this.getBillById(billId);
        billRepository.delete(billToDelete);
    }



}
