package com.example.onlinebankingapp.model.enums;

public enum AccountType {
    CHECKING_ACCOUNT,
    SAVING_ACCOUNT,
    CREDIT_CARD_ACCOUNT;

    /*
    private int value;

    AccountType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // you would also need a method to convert from int to AccountType
    public static AccountType fromValue(int value) {
        for (AccountType accountType : AccountType.values()) {
            if (accountType.getValue() == value) {
                return accountType;
            }
        }
        throw new IllegalArgumentException("Invalid AccountType value: " + value);
    }*/
}
