package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Transactional
    @Query("SELECT a FROM Account a WHERE a.customer.id = :customerId")
    List<Account> findAccountsByCustomerId(@Param("customerId") Long customerId);

    @Transactional
    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
    Optional<Account> findAccountByAccountNumber(String accountNumber);

    @Transactional
    @Query("SELECT a FROM Account a WHERE a.branch.id = :branchId")
    List<Account> findAccountsByBranchId(Long branchId);


    @Transactional
    @Query("SELECT sum(a.balance) FROM Account a")
    Double findTotalBalance();

    @Transactional
    @Query("SELECT sum(a.balance) FROM Account a WHERE a.customer.id = :customerId")
    Double findTotalBalanceByAccountNumber(Long customerId);

    @Transactional
    @Query("SELECT sum(a.balance) FROM Account a WHERE a.branch.id = :branchId")
    Double findTotalMoneyByBranchId(Long branchId);

    @Transactional
    @Query("SELECT avg(a.balance) FROM Account a WHERE a.accountType = :accountType")
    Double findAvgBalanceByAccountType(String accountType);

    @Transactional
    @Query("SELECT a FROM Account a ")
    List<Account> findLast10CreatedAccounts();


    @Transactional
    @Query("SELECT COUNT(a) FROM Account a WHERE a.accountType = :accountType")
    Integer findNumberOfAccountsByAccountType(String accountType);

    @Transactional
    @Query("SELECT MAX(a.balance) FROM Account a")
    Integer findMaxBalance();
}
