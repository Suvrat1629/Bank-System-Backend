package com.example.Bank_System.service;

import com.example.Bank_System.request.AccountRequest;
import com.example.Bank_System.request.LinkAccountsRequest;
import com.example.Bank_System.response.AccountResponse;
import lombok.Data;

import java.math.BigDecimal;


public interface AccountService {

    public AccountResponse createAccount(AccountRequest request);
    public BigDecimal checkAccountBalance(Long accountId);
    public BigDecimal calculateInterest(Long accountId, BigDecimal rate);
    public String linkMultipleAccounts(LinkAccountsRequest request);
    public AccountResponse updateAccountType(Long accountId, String newAccountType);
}
