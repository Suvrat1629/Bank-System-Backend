package com.example.Bank_System.service;

import com.example.Bank_System.model.Card;
import com.example.Bank_System.request.CardRequest;

import java.math.BigDecimal;
import java.util.List;


public interface CardService {

    public String requestNewCard(Long accountId, String cardType, BigDecimal dailyLimit, String pin);
    public String blockLostCard(String cardNumber);
    public String setCardLimits(String cardNumber, BigDecimal dailyLimit);
    public List<CardRequest> viewCardTransactions(Long accountId) throws Exception;
    public String changePin(String cardNumber,String currentPin, String newPin);
//    public String generateCardNumber();
}
