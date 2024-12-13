package com.example.Bank_System.service;

import com.example.Bank_System.model.Complaint;
import com.example.Bank_System.response.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface AdminTransactionService {
    String blockAccount(Long accountId);
    List<TransactionResponse> monitorTransactions(BigDecimal threshold);
//    String approveAccountCreation(Long accountId);
    String manageInterestRates(String accountType, BigDecimal newRate);
    List<Complaint> viewCustomerComplaints();
}
