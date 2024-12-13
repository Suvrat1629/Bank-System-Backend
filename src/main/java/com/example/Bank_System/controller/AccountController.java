package com.example.Bank_System.controller;

import com.example.Bank_System.request.AccountRequest;
import com.example.Bank_System.request.LinkAccountsRequest;
import com.example.Bank_System.response.AccountResponse;
import com.example.Bank_System.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {


    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request) {
        AccountResponse accountResponse = accountService.createAccount(request);
        return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
    }

    // Check Account Balance
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BigDecimal> checkAccountBalance(@PathVariable Long accountId) {
        BigDecimal balance = accountService.checkAccountBalance(accountId);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    // Update Account Type
    @PutMapping("/{accountId}/type")
    public ResponseEntity<AccountResponse> updateAccountType(
            @PathVariable Long accountId,
            @RequestParam String newAccountType) {
        AccountResponse accountResponse = accountService.updateAccountType(accountId, newAccountType);
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }

    // Calculate Interest
    @GetMapping("/{accountId}/interest")
    public ResponseEntity<BigDecimal> calculateInterest(
            @PathVariable Long accountId,
            @RequestParam BigDecimal rate) {
        BigDecimal interest = accountService.calculateInterest(accountId, rate);
        return new ResponseEntity<>(interest, HttpStatus.OK);
    }

    // Link Multiple Accounts
    @PostMapping("/link")
    public ResponseEntity<String> linkMultipleAccounts(@RequestBody LinkAccountsRequest request) {
        String responseMessage = accountService.linkMultipleAccounts(request);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
