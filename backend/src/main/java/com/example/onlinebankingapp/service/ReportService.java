package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.Account;
import com.example.onlinebankingapp.model.entities.Branch;
import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.model.entities.Transaction;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReportService {

    private final CustomerService customerService;
    private final TransactionService transactionService;
    private final BranchService branchService;
    private final AccountService accountService;

    public ReportService(CustomerService customerService,
                         TransactionService transactionService,
                         BranchService branchService,
                         AccountService accountService) {
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.branchService = branchService;
        this.accountService = accountService;
    }

    public String generateTotalDepositsWithinPeriodReport(@NotNull Date startDate, @NotNull Date endDate) {
        StringBuilder report = new StringBuilder();

        List<Transaction> transactionList = transactionService.getDepositsWithinPeriod(startDate, endDate);
        Double totalDeposits = transactionService.getTotalDepositsWithinPeriod(startDate, endDate);

        for ( Transaction transaction : transactionList ) {
            report.append("Transaction ID: ").append(transaction.getId()).append("\n");
            report.append("Transaction Date: ").append(transaction.getTransactionDate()).append("\n");
            report.append("Transaction Type: ").append(transaction.getTransactionType()).append("\n");
            report.append("Transaction Amount: ").append(transaction.getAmount()).append("\n");
            report.append("From Account: ").append(transaction.getFromAccount().getAccountNumber()).append("\n");
            report.append("To Account: ").append(transaction.getToAccount().getAccountNumber()).append("\n");
            report.append("\n");
        }
        report.append("\n");
        report.append("There are total of ").append(transactionList.size()).append(" deposits within the period.\n");
        report.append("Total Deposits: ").append(totalDeposits).append("\n");

        return report.toString();

    }

    public String generateCustomerAccountReport() {

        List<Customer> customerList = customerService.getAll();
        StringBuilder report = new StringBuilder();

        for (Customer customer : customerList) {
            report.append("Customer Name: ").append(customer.getFirstName()).append(" ").append(customer.getLastName()).append("\n");
            report.append("Customer Email: ").append(customer.getEmail()).append("\n");
            report.append("Customer Phone: ").append(customer.getPhone()).append("\n");
            report.append("Customer Address: ").append(customer.getAddress()).append("\n");
            report.append("Customer Accounts: \n");
            for (Account account : customer.getAccounts()) {
                report.append("Account Number: ").append(account.getAccountNumber()).append("\n");
                report.append("Account Type: ").append(account.getAccountType()).append("\n");
                report.append("Account Balance: ").append(account.getBalance()).append("\n");
                report.append("\n");
            }
            report.append("\n");
        }

        return report.toString();
    }


    public String generateBranchReport(Long branchId) {
        StringBuilder report = new StringBuilder();

        Branch branch = branchService.getBranchById(branchId);
        List<Account> accounts = accountService.getAccountsByBranchId(branchId);
        Integer checkingAccountCount = accounts.stream().filter(account -> account.getAccountType().toString().equals("CHECKING_ACCOUNT")).toList().size();
        Integer savingAccountCount = accounts.stream().filter(account -> account.getAccountType().toString().equals("SAVING_ACCOUNT")).toList().size();
        Integer creditCardAccountCount = accounts.stream().filter(account -> account.getAccountType().toString().equals("CREDIT_CARD_ACCOUNT")).toList().size();

        report.append("Branch Name: ").append(branch.getName()).append("\n");
        report.append("Branch Address: ").append(branch.getAddress()).append("\n");

        for (Account account : accounts) {
            report.append("Account Number: ").append(account.getAccountNumber()).append("\n");
            report.append("Account Type: ").append(account.getAccountType()).append("\n");
            report.append("Account Balance: ").append(account.getBalance()).append("\n");
            report.append("\n");
        }
        report.append("\n\n");

        report.append("Total number of accounts in this branch is ").append(accounts.size()).append("\n");
        report.append("Total number of checking accounts in this branch is ").append(checkingAccountCount).append("\n");
        report.append("Total number of saving accounts in this branch is ").append(savingAccountCount).append("\n");
        report.append("Total number of credit card accounts in this branch is ").append(creditCardAccountCount).append("\n");

        return report.toString();
    }
}
