package com.example.Bank_System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String type; // "DEPOSIT", "WITHDRAWAL", "TRANSFER"

    private LocalDateTime timestamp;

    private String description;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "target_account_id", nullable = true)
    private Account targetAccount; // For transfers

    @ManyToOne
    @JoinColumn(name = "card_id",referencedColumnName = "id", nullable = false)
    private Card card;

    public Transaction(){

    }


    public Transaction(Long id, BigDecimal amount, String type, LocalDateTime timestamp, String description, Account account, Account targetAccount) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.description = description;
        this.account = account;
        this.targetAccount = targetAccount;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Account targetAccount) {
        this.targetAccount = targetAccount;
    }

}
