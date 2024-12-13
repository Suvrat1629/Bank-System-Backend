package com.example.Bank_System.service;

import com.example.Bank_System.request.DepositRequest;
import com.example.Bank_System.request.ScheduleRecurringPaymentRequest;
import com.example.Bank_System.request.TransferRequest;
import com.example.Bank_System.response.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    String transferFunds(TransferRequest transferRequest);

    String depositMoney(DepositRequest depositRequest);

    String withdrawMoney(Long accountId, BigDecimal amount);

    List<TransactionResponse> generateMiniStatement(Long accountId, int numberOfTransactions);

    String scheduleRecurringPayment(ScheduleRecurringPaymentRequest request);
}
