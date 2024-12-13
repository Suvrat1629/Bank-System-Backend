package com.example.Bank_System.service;

import com.example.Bank_System.model.Account;
import com.example.Bank_System.model.Transaction;
import com.example.Bank_System.response.TransactionResponse;
import com.example.Bank_System.model.Complaint;
import com.example.Bank_System.repository.AccountRepository;
import com.example.Bank_System.repository.TransactionRepository;
import com.example.Bank_System.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminTransactionServiceImpl implements AdminTransactionService {


    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ComplaintRepository complaintRepository;

    public AdminTransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, ComplaintRepository complaintRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.complaintRepository = complaintRepository;
    }

    @Override
    public String blockAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setBlocked(true);
        accountRepository.save(account);
        return "Account successfully blocked.";
    }

    @Override
    public List<TransactionResponse> monitorTransactions(BigDecimal thresholdAmount) {
        List<Transaction> transactions = transactionRepository.findByAmountGreaterThan(thresholdAmount);
        return transactions.stream().map(tx -> new TransactionResponse(
                tx.getId(),
                tx.getAmount(),
                tx.getType(),
                tx.getTimestamp(),
                tx.getDescription()
        )).collect(Collectors.toList());
    }

//    @Override
//    public String approveAccountCreation(Long accountId) {
//        Account account = accountRepository.findById(accountId)
//                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
//        account.setApproved(true);
//        accountRepository.save(account);
//        return "Account creation approved.";
//    }

    @Override
    public String manageInterestRates(String accountType, BigDecimal newInterestRate) {
        List<Account> accounts = accountRepository.findByAccountType(accountType);
        accounts.forEach(account -> account.setInterestRate(newInterestRate));
        accountRepository.saveAll(accounts);
        return "Interest rates updated for account type: " + accountType;
    }

    @Override
    public List<Complaint> viewCustomerComplaints() {
        return complaintRepository.findAll();
    }
}

