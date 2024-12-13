package com.example.Bank_System.repository;

import com.example.Bank_System.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
