package com.example.Bank_System.repository;

import com.example.Bank_System.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTop5ByAccountIdOrderByTimestampDesc(Long accountId);

    List<Transaction> findTop10ByAccountIdOrderByTimestampDesc(Long accountId);
}
