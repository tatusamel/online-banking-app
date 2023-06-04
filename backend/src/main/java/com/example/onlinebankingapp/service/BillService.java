package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.entities.Bill;
import com.example.onlinebankingapp.model.enums.BillStatus;
import com.example.onlinebankingapp.model.repositories.BillRepository;
import com.example.onlinebankingapp.model.requests.BillRequest;
import com.example.onlinebankingapp.view.converter.BillDTOConverter;
import com.example.onlinebankingapp.view.dto.BillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final AccountService accountService;
    private final BillDTOConverter billDTOConverter;
    private final UserActionService userActionService;

    @Autowired
    public BillService(BillRepository billRepository,
                       AccountService accountService,
                       BillDTOConverter billDTOConverter,
                       UserActionService userActionService)
    {
        this.billRepository = billRepository;
        this.accountService = accountService;
        this.billDTOConverter = billDTOConverter;
        this.userActionService = userActionService;
    }

    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    public Bill getBillById(Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new NoSuchElementException("No Bill with id: " + billId));
    }

    public BillDTO insertBill(BillRequest billRequest) {
        Bill bill = new Bill();
        mapRequestToBill(billRequest, bill);
        Bill savedBill = billRepository.save(bill);
        return billDTOConverter.convertToDto(savedBill);
    }

    public BillDTO updateBill(Long billId, BillRequest billRequest) {
        Bill billToUpdate = this.getBillById(billId);
        mapRequestToBill(billRequest, billToUpdate);
        Bill updatedBill = billRepository.save(billToUpdate);
        return billDTOConverter.convertToDto(updatedBill);
    }

    public void deleteBill(Long billId) {
        Bill billToDelete = this.getBillById(billId);
        billRepository.delete(billToDelete);
    }


    public List<BillDTO> getUnpaidBillsByAccountId(String accountNumber) {
        List<Bill> bills = billRepository.findUnpaidBillsByAccountId(accountNumber);
        return bills.stream()
                .map(billDTOConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BillDTO> getPaidBillsByAccountId(String accountNumber) {
        List<Bill> bills = billRepository.findPaidBillsByAccountId(accountNumber);
        return bills.stream()
                .map(billDTOConverter::convertToDto)
                .collect(Collectors.toList());
    }



    public Bill mapRequestToBill(BillRequest billRequest, Bill bill) {
        Account account = accountService.getAccountById(billRequest.getAccountId());

        bill.setAccount(account);
        bill.setName(billRequest.getName());
        bill.setAmount(billRequest.getAmount());
        bill.setDueDate(billRequest.getDueDate());

        if ( billRequest.getStatus() == null ) {
            bill.setStatus(BillStatus.UNPAID);
        } else {
            bill.setStatus(billRequest.getStatus());
        }

        return bill;
    }

    public BillDTO payBill(Long billId) {
        Bill bill = this.getBillById(billId);

        if ( bill.getStatus().equals(BillStatus.PAID) ) {
            throw new IllegalArgumentException("Bill is already paid");
        }

        if ( bill.getAmount() > bill.getAccount().getBalance() ) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        bill.getAccount().setBalance(bill.getAccount().getBalance() - bill.getAmount());
        bill.setStatus(BillStatus.PAID);

        accountService.saveAccount(bill.getAccount());
        userActionService.billPaidAction(bill.getAccount().getCustomer().getId(), bill.getId());
        return billDTOConverter.convertToDto(billRepository.save(bill));
    }
}
