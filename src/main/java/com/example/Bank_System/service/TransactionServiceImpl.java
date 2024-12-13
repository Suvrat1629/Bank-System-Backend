package com.example.Bank_System.service;

import com.example.Bank_System.jobs.RecurringPaymentJob;
import com.example.Bank_System.model.Account;
import com.example.Bank_System.model.Transaction;
import com.example.Bank_System.repository.AccountRepository;
import com.example.Bank_System.repository.TransactionRepository;
import com.example.Bank_System.request.DepositRequest;
import com.example.Bank_System.request.ScheduleRecurringPaymentRequest;
import com.example.Bank_System.request.TransferRequest;
import com.example.Bank_System.response.TransactionResponse;
import jakarta.transaction.Transactional;
import org.quartz.*;
import org.springframework.stereotype.Service;
import com.example.Bank_System.config.QuartzConfig;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final Scheduler scheduler;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, Scheduler scheduler) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.scheduler=scheduler;
    }

    @Override
    @Transactional
    public String transferFunds(TransferRequest transferRequest) {
        Long fromAccountId = transferRequest.getFromAccountId();
        Long toAccountId = transferRequest.getToAccountId();
        BigDecimal amount = BigDecimal.valueOf(transferRequest.getAmount());
        String description = transferRequest.getDescription();

        // Fetch the accounts from the repository
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Target account not found"));

        // Check for sufficient balance
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update account balances
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Log the transaction
        Transaction transaction = new Transaction(
                null,
                amount,
                "TRANSFER",
                LocalDateTime.now(),
                description,
                fromAccount,
                toAccount
        );
        transactionRepository.save(transaction);

        return "Transfer successful";
    }


    @Override
    @Transactional
    public String depositMoney(DepositRequest depositRequest) {
        // Retrieve accountId and amount from DepositRequest
        Long accountId = depositRequest.getAccountId();
        BigDecimal amount = depositRequest.getAmount();

        // Find the account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Update the balance
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        // Create a transaction record
        Transaction transaction = new Transaction(
                null,
                amount,
                "DEPOSIT",
                LocalDateTime.now(),
                "Deposit",
                account,
                null
        );
        transactionRepository.save(transaction);

        return "Deposit successful";
    }


    @Override
    @Transactional
    public String withdrawMoney(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction(null, amount, "WITHDRAWAL", LocalDateTime.now(), "Withdrawal", account, null);
        transactionRepository.save(transaction);

        return "Withdrawal successful";
    }

    @Override
    public List<TransactionResponse> generateMiniStatement(Long accountId, int numberOfTransactions) {
        List<Transaction> transactions;
        if (numberOfTransactions == 5) {
            transactions = transactionRepository.findTop5ByAccountIdOrderByTimestampDesc(accountId);
        } else {
            transactions = transactionRepository.findTop10ByAccountIdOrderByTimestampDesc(accountId);
        }

        return transactions.stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getTimestamp(),
                        transaction.getDescription()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public String scheduleRecurringPayment(ScheduleRecurringPaymentRequest request) {
        try {
            // Extract values from the request object
            Long accountId = request.getAccountId();
            BigDecimal amount = request.getAmount();
            String description = request.getDescription();
            String frequency = request.getFrequency();

            // Create a unique JobKey and TriggerKey
            JobKey jobKey = new JobKey("RecurringPaymentJob-" + accountId, "RecurringPayments");
            TriggerKey triggerKey = new TriggerKey("RecurringPaymentTrigger-" + accountId, "RecurringPayments");

            // Define the JobDetail with necessary parameters
            JobDetail jobDetail = JobBuilder.newJob(RecurringPaymentJob.class)
                    .withIdentity(jobKey)
                    .usingJobData("accountId", accountId)
                    .usingJobData("amount", amount.doubleValue())
                    .usingJobData("description", description)
                    .build();

            // Define the Trigger based on the frequency
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .startNow()
                    .withSchedule(parseFrequency(frequency))
                    .build();

            // Schedule the job
            if (!scheduler.checkExists(jobKey)) {
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                return "A recurring payment is already scheduled for this account.";
            }

            return "Recurring payment scheduled successfully.";
        } catch (SchedulerException e) {
            e.printStackTrace();
            return "Error while scheduling recurring payment.";
        }
    }


    // Helper method to parse frequency into Quartz ScheduleBuilder
    private ScheduleBuilder<?> parseFrequency(String frequency) {
        switch (frequency.toLowerCase()) {
            case "daily":
                return SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24).repeatForever();
            case "weekly":
                return SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(168).repeatForever();
            case "monthly":
                return CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInMonths(1);
            default:
                throw new IllegalArgumentException("Invalid frequency: " + frequency);
        }
    }
}
