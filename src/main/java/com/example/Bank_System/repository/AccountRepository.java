package com.example.Bank_System.repository;

import com.example.Bank_System.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByAccountType(String accountType);

    @Query("UPDATE Account SET interestRate = :rate WHERE accountType = :type")
    void updateInterestRateForAccountType(String type, BigDecimal rate);

    @Query("SELECT a FROM Account a WHERE a.accountType = :accountType AND a.blocked = false")
    List<Account> findUnblockedAccountsByType(@Param("accountType") String accountType);

}
