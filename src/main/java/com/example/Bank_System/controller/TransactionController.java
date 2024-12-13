package com.example.Bank_System.controller;

import com.example.Bank_System.request.DepositRequest;
import com.example.Bank_System.request.ScheduleRecurringPaymentRequest;
import com.example.Bank_System.request.TransferRequest;
import com.example.Bank_System.request.WithdrawRequest;
import com.example.Bank_System.response.TransactionResponse;
import com.example.Bank_System.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@Valid @RequestBody TransferRequest transferRequest) {
        String result = transactionService.transferFunds(transferRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> depositMoney(@RequestBody DepositRequest depositRequest) {
        // Call the service layer with the deposit request
        String message = transactionService.depositMoney(depositRequest);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawMoney(@RequestBody WithdrawRequest withdrawRequest) {
        String message = transactionService.withdrawMoney(withdrawRequest.getAccountId(), withdrawRequest.getAmount());
        return ResponseEntity.ok(message);
    }


    @GetMapping("/{accountId}/mini-statement")
    public ResponseEntity<List<TransactionResponse>> getMiniStatement(
            @PathVariable Long accountId,
            @RequestBody Map<String, Integer> requestBody) {
        int numberOfTransactions = requestBody.get("numberOfTransactions");
        List<TransactionResponse> response = transactionService.generateMiniStatement(accountId, numberOfTransactions);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/schedule-payment")
    public ResponseEntity<String> scheduleRecurringPayment(
            @RequestBody ScheduleRecurringPaymentRequest request) {
        String message = transactionService.scheduleRecurringPayment(request);
        return ResponseEntity.ok(message);
    }

}
