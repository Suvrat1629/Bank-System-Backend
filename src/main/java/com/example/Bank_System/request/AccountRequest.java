package com.example.Bank_System.request;


import java.math.BigDecimal;

public class AccountRequest {

    private Long customerId;
    private String accountType;

    private BigDecimal InitialDeposit;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getInitialDeposit() {
        return InitialDeposit;
    }

    public void setInitialDeposit(BigDecimal initialDeposit) {
        InitialDeposit = initialDeposit;
    }
}
