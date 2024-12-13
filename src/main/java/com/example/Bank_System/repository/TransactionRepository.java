package com.example.Bank_System.repository;

import com.example.Bank_System.model.Transaction;
import com.example.Bank_System.response.TransactionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTop5ByAccountIdOrderByTimestampDesc(Long accountId);

    List<Transaction> findTop10ByAccountIdOrderByTimestampDesc(Long accountId);

    @Query("SELECT new com.example.Bank_System.response.TransactionResponse(t.id, t.accountId, t.amount, t.timestamp) FROM Transaction t WHERE t.amount > :threshold")
    List<TransactionResponse> findTransactionsAboveThreshold(BigDecimal threshold);

    List<Transaction> findByAmountGreaterThan(BigDecimal thresholdAmount);
}
