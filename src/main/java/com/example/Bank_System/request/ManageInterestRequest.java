package com.example.Bank_System.request;

import java.math.BigDecimal;

public class ManageInterestRequest {

    private String accountType;
    private BigDecimal newRate;

    // Getters and setters
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getNewRate() {
        return newRate;
    }

    public void setNewRate(BigDecimal newRate) {
        this.newRate = newRate;
    }
}
