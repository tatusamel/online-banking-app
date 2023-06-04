package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.service.ReportService;
import com.example.onlinebankingapp.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final TransactionService transactionService;
    private final ReportService reportService;

    public ReportController(TransactionService transactionService,
                            ReportService reportService) {
        this.transactionService = transactionService;
        this.reportService = reportService;
    }

    @GetMapping("/total-deposits-report")
    public ResponseEntity<String> getTotalDeposits(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                   @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return new ResponseEntity<>(reportService.generateTotalDepositsWithinPeriodReport(startDate, endDate), HttpStatus.OK);
    }

    // get Customers and accounts associated with them
    @GetMapping("/customers-accounts-report")
    public ResponseEntity<String> getCustomersAndAccounts() {
        return new ResponseEntity<>(reportService.generateCustomerAccountReport(), HttpStatus.OK);
    }

    @GetMapping("/branch-report/{branchId}")
    public ResponseEntity<String> getBranchReport(@PathVariable Long branchId) {
        return new ResponseEntity<>(reportService.generateBranchReport(branchId), HttpStatus.OK);
    }

}
