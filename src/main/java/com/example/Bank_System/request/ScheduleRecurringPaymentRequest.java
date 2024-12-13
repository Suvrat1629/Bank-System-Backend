package com.example.Bank_System.request;

import java.math.BigDecimal;

public class ScheduleRecurringPaymentRequest {
    private Long accountId;
    private BigDecimal amount;
    private String description;
    private String frequency;

    // Getters and setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}

