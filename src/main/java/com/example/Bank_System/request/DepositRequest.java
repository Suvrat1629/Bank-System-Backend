package com.example.Bank_System.request;


import java.math.BigDecimal;

public class DepositRequest {
    private Long accountId;
    private BigDecimal amount;

    // Constructors
    public DepositRequest() {}

    public DepositRequest(Long accountId, BigDecimal amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    // Getters and Setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

