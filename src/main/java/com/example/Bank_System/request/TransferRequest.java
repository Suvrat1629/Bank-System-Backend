package com.example.Bank_System.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class TransferRequest {

    @NotNull(message = "Source account ID cannot be null")
    private Long fromAccountId;

    @NotNull(message = "Target account ID cannot be null")
    private Long toAccountId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Transfer amount must be at least 0.01")
    private Double amount;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    public TransferRequest() {

    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
