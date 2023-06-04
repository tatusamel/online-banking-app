package com.example.onlinebankingapp.model.repositories;

import com.example.onlinebankingapp.model.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    /*
    this method returns all unpaid bills for a given customer if due date of the bill is after the current date
    which means due date hasn't passed yet
     */
    @Transactional
    @Query("SELECT b FROM Bill b WHERE b.account.accountNumber = :accountNumber AND b.dueDate > CURRENT_DATE AND b.status = 'UNPAID'")
    List<Bill> findUnpaidBillsByAccountId(String accountNumber);

    @Transactional
    @Query("SELECT b FROM Bill b WHERE b.account.accountNumber = :accountNumber AND b.status = 'PAID'")
    List<Bill> findPaidBillsByAccountId(String accountNumber);

}
