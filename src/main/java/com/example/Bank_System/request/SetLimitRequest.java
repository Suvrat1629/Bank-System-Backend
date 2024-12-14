package com.example.Bank_System.request;

import java.math.BigDecimal;

public class SetLimitRequest {
    private String cardNumber;
    private BigDecimal dailyLimit;

    // Getters and setters
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }
}

