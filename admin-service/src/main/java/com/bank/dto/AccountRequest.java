package com.bank.dto;

public class AccountRequest {

    private String customerId;
    private String accountType;
    private double balance;

    public AccountRequest() {
    }

    public AccountRequest(String customerId, String accountType, double balance) {
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
