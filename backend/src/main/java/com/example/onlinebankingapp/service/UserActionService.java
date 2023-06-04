package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.Transaction;
import com.example.onlinebankingapp.model.entities.UserAction;
import com.example.onlinebankingapp.model.repositories.UserActionRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class UserActionService {

    private final UserActionRepository userActionRepository;

    public UserActionService(UserActionRepository userActionRepository) {
        this.userActionRepository = userActionRepository;
    }

    public UserAction saveUserAction(UserAction userAction) {
        return userActionRepository.save(userAction);
    }

    public UserAction accountCreatedAction(Long userId, String accountNumber) {
        UserAction action = new UserAction();
        action.setUserId(userId);
        action.setAction("Account created with account number: " + accountNumber
                + " by customer with id: " + userId +".");
        return userActionRepository.save(action);
    }

    public UserAction accountUpdatedAction(Long userId, String accountNumber) {
        UserAction action = new UserAction();
        action.setUserId(userId);
        action.setAction("Account updated with account number: " + accountNumber
                + " by customer with id: " + userId +".");
        return userActionRepository.save(action);
    }

    public UserAction accountDeletedAction(Long userId, String accountNumber) {
        UserAction action = new UserAction();
        action.setUserId(userId);
        action.setAction("Account deleted with account number: " + accountNumber
                + " by customer with id: " + userId +".");
        return userActionRepository.save(action);
    }

    public UserAction billPaidAction(Long userId, Long billId) {
        UserAction action = new UserAction();
        action.setAction("Bill paid with id: " + billId
                + " by customer with id: " + userId +".");
        action.setUserId(userId);
        return userActionRepository.save(action);
    }

    public UserAction transactionCreatedAction(Long userId, Transaction transaction) {
        UserAction action = new UserAction();
        action.setUserId(userId);
        action.setAction("Transaction created with id: " + transaction.getId()
                + " and type: " + transaction.getTransactionType()
                + " from account with number: " + transaction.getFromAccount().getAccountNumber()
                + " to account with number: " + transaction.getToAccount().getAccountNumber()
                + " in amount of: " + transaction.getAmount() + " by customer with id: " + userId +".");
        return userActionRepository.save(action);
    }

    public UserAction transactionUpdatedAction(Long userId, Transaction transaction) {
        UserAction action = new UserAction();
        action.setUserId(userId);
        action.setAction("Transaction updated with id: " + transaction.getId()
                + " and type: " + transaction.getTransactionType()
                + " from account with number: " + transaction.getFromAccount().getAccountNumber()
                + " to account with number: " + transaction.getToAccount().getAccountNumber()
                + " in amount of: " + transaction.getAmount() + " by customer with id: " + userId +".");
        return userActionRepository.save(action);
    }

    public UserAction transactionDeletedAction(Long userId, Transaction transaction) {
        UserAction action = new UserAction();
        action.setUserId(userId);
        action.setAction("Transaction deleted with id: " + transaction.getId()
                + " and type: " + transaction.getTransactionType()
                + " from account with number: " + transaction.getFromAccount().getAccountNumber()
                + " to account with number: " + transaction.getToAccount().getAccountNumber()
                + " in amount of: " + transaction.getAmount() + " by customer with id: " + userId +".");
        return userActionRepository.save(action);
    }

    public UserAction userLoginSuccessfulAction(Long userId) {
        UserAction action = new UserAction();
        action.setUserId(userId);
        action.setAction("User with id: " + userId + " logged in successfully."
                + " Date: " + new Date(System.currentTimeMillis()));
        return userActionRepository.save(action);
    }

    public UserAction userLoginFailedAction(Long userId) {
        UserAction action = new UserAction();
        action.setUserId(userId);
        action.setAction("User with id: " + userId + " failed to log in."
                + " Date: " + new Date(System.currentTimeMillis()));
        return userActionRepository.save(action);
    }
    public UserAction userCreatedAction(Long userId) {
        UserAction action = new UserAction();
        action.setUserId(userId);
        action.setAction("User with id: " + userId + " created his profile."
                + " Date: " + new Date(System.currentTimeMillis()));
        return userActionRepository.save(action);
    }
    public UserAction userUpdatedAction(Long userId) {
        UserAction action = new UserAction();
        action.setUserId(userId);
        action.setAction("User with id: " + userId + " updated his profile."
                + " Date: " + new Date(System.currentTimeMillis()));
        return userActionRepository.save(action);
    }

    public UserAction userDeletedAction(Long userId) {
        UserAction action = new UserAction();
        action.setUserId(userId);
        action.setAction("User with id: " + userId + " deleted his profile."
                + " Date: " + new Date(System.currentTimeMillis()));
        return userActionRepository.save(action);
    }
}
