package com.example.Bank_System.service;

import com.example.Bank_System.config.JwtProvider;
import com.example.Bank_System.exception.CustomException;
import com.example.Bank_System.model.Account;
import com.example.Bank_System.model.Customer;
import com.example.Bank_System.repository.AccountRepository;
import com.example.Bank_System.repository.CustomerRepository;
import com.example.Bank_System.request.AccountRequest;
import com.example.Bank_System.request.LinkAccountsRequest;
import com.example.Bank_System.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    private final CustomerRepository customerRepository;

    private final AccountRepository accountRepository;

    public AccountServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, JwtProvider jwtProvider){
        this.customerRepository=customerRepository;
        this.accountRepository=accountRepository;
    }

    // Create Account
    public AccountResponse createAccount(AccountRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomException("Customer not found"));

        Account account = new Account();
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getInitialDeposit()); // Start with zero balance
        account.setCustomer(customer);

        Account savedAccount = accountRepository.save(account);
        return mapToResponse(savedAccount);
    }

    // Check Account Balance
    public BigDecimal checkAccountBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CustomException("Account not found"));

        return account.getBalance();
    }

    // Update Account Type
    public AccountResponse updateAccountType(Long accountId, String newAccountType) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CustomException("Account not found"));

        if (account.getAccountType().equals(newAccountType)) {
            throw new CustomException("The account type is already " + newAccountType);
        }

        account.setAccountType(newAccountType);
        Account updatedAccount = accountRepository.save(account);
        return mapToResponse(updatedAccount);
    }

    // Calculate Interest
    public BigDecimal calculateInterest(Long accountId, BigDecimal rate) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CustomException("Account not found"));

        if (!account.getAccountType().equals("Savings")) {
            throw new CustomException("Interest calculation is only applicable for Savings accounts");
        }

        BigDecimal interest = account.getBalance().multiply(rate);
        return interest;
    }

    // Link Multiple Accounts
    public String linkMultipleAccounts(LinkAccountsRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomException("Customer not found"));

        List<Account> accounts = accountRepository.findAllById(request.getAccountIds());

        for (Account account : accounts) {
            account.setCustomer(customer);
        }

        accountRepository.saveAll(accounts);
        return "Accounts linked successfully to customer ID " + request.getCustomerId();
    }

    // Helper method to map Account to AccountResponse
    private AccountResponse mapToResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setAccountId(account.getId());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        response.setCustomerId(account.getCustomer().getId());
        return response;
    }
}
