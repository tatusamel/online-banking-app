package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.model.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Transactional
    @Query("SELECT t FROM Transaction t WHERE t.toAccount.accountNumber = :accountNumber")
    List<Transaction> findIncomingTransactionsByAccountNumber(String accountNumber);

    @Transactional
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.accountNumber = :accountNumber")
    List<Transaction> findOutgoingTransactionsByAccountNumber(String accountNumber);

    @Transactional
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.transactionType = 'DEPOSIT' AND t.transactionDate BETWEEN :startDate AND :endDate")
    Double findTotalDepositsWithinPeriod(Date startDate, Date endDate);

    @Transactional
    @Query("SELECT t FROM Transaction t WHERE t.transactionType = 'DEPOSIT' AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findDepositsWithinPeriod(Date startDate, Date endDate);

    /*
    @Transactional
    @Query("SELECT t, COUNT(t.id) as transactionCount FROM Transaction t WHERE t.transactionDate >= :date GROUP BY t.fromAccount.id ORDER BY COUNT(t.id) DESC")
    List<Object[]> findCustomersWithTheMostNumberOfTransactions(Date date);

     */

    @Transactional
    @Query("SELECT t.fromAccount.id FROM Transaction t WHERE t.transactionDate >= :date GROUP BY t.fromAccount.id ORDER BY COUNT(t.id) DESC")
    List<Long> findCustomersWithTheMostNumberOfTransactions(Date date);
}
