package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Bill;
import com.example.onlinebankingapp.model.repositories.BillRepository;
import com.example.onlinebankingapp.model.requests.BillRequest;
import com.example.onlinebankingapp.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping
    public ResponseEntity<List<Bill>> listAllBills() {
        List<Bill> bills = billService.listAllBills();
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @GetMapping("/{bill_id}")
    public ResponseEntity<Bill> getBillById(@PathVariable("bill_id") Long billId) {
        Bill bill = billService.getBillById(billId);
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Bill> insertBill(@RequestBody BillRequest billRequest) {
        Bill bill = billService.insertBill(billRequest);
        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }

    @PutMapping("/update/{bill_id}")
    public ResponseEntity<Bill> updateBill(@PathVariable("bill_id") Long billId, @RequestBody BillRequest billRequest) {
        Bill bill = billService.updateBill(billId, billRequest);
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{bill_id}")
    public ResponseEntity<Void> deleteBill(@PathVariable("bill_id") Long billId) {
        billService.deleteBill(billId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
