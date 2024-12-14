package com.example.Bank_System.service;

import com.example.Bank_System.model.Account;
import com.example.Bank_System.model.Card;
import com.example.Bank_System.model.Transaction;
import com.example.Bank_System.repository.AccountRepository;
import com.example.Bank_System.repository.CardRepository;
import com.example.Bank_System.request.CardRequest;
import com.example.Bank_System.request.TransactionRequest;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public CardServiceImpl(CardRepository cardRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public String requestNewCard(Long accountId, String cardType, BigDecimal dailyLimit, String pin) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for ID: " + accountId));

        Card card = new Card();

        card.setAccount(account); // Placeholder for Account reference
        card.setCardType(cardType);
        card.setDailyLimit(dailyLimit);
        card.setPin(passwordEncoder.encode(pin));
        card.setCardNumber(generateCardNumber());
        card.setBlocked(false);
        card.setExpiryDate(generateExpiryDate()); // Set expiry date
        cardRepository.save(card);
        return "Card requested successfully. Card Number: " + card.getCardNumber();
    }

    public String blockLostCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (card == null) {
            throw new IllegalArgumentException("Card not found.");
        }
        if (isCardExpired(card)) {
            throw new IllegalArgumentException("Operation failed. The card has expired.");
        }
        card.setBlocked(true);
        cardRepository.save(card);
        return "Card has been blocked successfully.";
    }

    public String setCardLimits(String cardNumber, BigDecimal dailyLimit) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (card == null) {
            throw new IllegalArgumentException("Card not found.");
        }
        if (isCardExpired(card)) {
            throw new IllegalArgumentException("Operation failed. The card has expired.");
        }
        card.setDailyLimit(dailyLimit);
        cardRepository.save(card);
        return "Card daily limit has been updated.";
    }

    public List<CardRequest> viewCardTransactions(Long accountId) throws Exception {
        try {
            // Fetch cards from repository by account ID
            List<Card> cards = cardRepository.findByAccountId(accountId);

            // Handle case where no cards are found for the given account ID
            if (cards == null || cards.isEmpty()) {
                throw new Exception("No cards found for account ID: " + accountId);
            }

            // Optionally block expired cards
            cards.forEach(card -> {
                if (isCardExpired(card)) {
                    card.setBlocked(true); // Block expired cards
                }
            });

            // Map the Card entities to CardRequest DTOs
            List<CardRequest> cardRequests = cards.stream()
                    .map(card -> {
                        // Create CardRequest DTO
                        CardRequest cardRequest = new CardRequest();
                        cardRequest.setAccountId(card.getAccount().getId());
                        cardRequest.setCardType(card.getCardType());
                        cardRequest.setDailyLimit(card.getDailyLimit());
                        cardRequest.setPin(card.getPin());

                        // Fetch transactions associated with the account
                        List<Transaction> transactions = card.getTransactions();

                        // Map the transactions to TransactionRequest DTOs
                        List<TransactionRequest> transactionRequests = transactions.stream()
                                .map(transaction -> {
                                    TransactionRequest transactionRequest = new TransactionRequest();
                                    transactionRequest.setTransactionId(transaction.getId());
                                    transactionRequest.setAmount(transaction.getAmount());
                                    transactionRequest.setTransactionDate(transaction.getTimestamp());
                                    transactionRequest.setDescription(transaction.getDescription());
                                    return transactionRequest;
                                })
                                .collect(Collectors.toList());

                        // Set the transactions in the CardRequest DTO (if needed)
                        cardRequest.setTransactions(transactionRequests);

                        return cardRequest;
                    })
                    .collect(Collectors.toList());

            return cardRequests;

        }catch (Exception e) {
                // Log the error and throw a generic exception if necessary
                throw new Exception("An error occurred while processing the card transactions", e);
            }
        }

    public String changePin(String cardNumber, String currentPin, String newPin) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (card == null) {
            throw new IllegalArgumentException("Card not found.");
        }
        if (isCardExpired(card)) {
            throw new IllegalArgumentException("Operation failed. The card has expired.");
        }
        if (!passwordEncoder.matches(currentPin, card.getPin())) {
            throw new IllegalArgumentException("Invalid current PIN.");
        }
        card.setPin(passwordEncoder.encode(newPin));
        cardRepository.save(card);
        return "Card PIN has been updated successfully.";
    }

    private String generateCardNumber() {
        return String.valueOf(System.nanoTime()).substring(0, 15);
    }

    private LocalDate generateExpiryDate() {
        return LocalDate.now().plusYears(5); // Set expiry to 5 years from now
    }

    private boolean isCardExpired(Card card) {
        return card.getExpiryDate().isBefore(LocalDate.now());
    }
}
