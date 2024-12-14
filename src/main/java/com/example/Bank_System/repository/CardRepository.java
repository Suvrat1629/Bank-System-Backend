package com.example.Bank_System.repository;

import com.example.Bank_System.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByCardNumber(String cardNumber);

    List<Card> findByAccountId(Long accountId);
}
