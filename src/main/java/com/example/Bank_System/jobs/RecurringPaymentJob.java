package com.example.Bank_System.jobs;

import com.example.Bank_System.service.TransactionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import java.math.BigDecimal;

public class RecurringPaymentJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Retrieve job parameters from the JobDataMap
        Long accountId = context.getJobDetail().getJobDataMap().getLong("accountId");
        BigDecimal amount = BigDecimal.valueOf(context.getJobDetail().getJobDataMap().getDouble("amount"));
        String description = context.getJobDetail().getJobDataMap().getString("description");

        // Simulate accessing required services
        TransactionService transactionService = null;
        try {
            transactionService = (TransactionService) context.getScheduler().getContext().get("transactionService");
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        try {
            // Process the payment
            String result = transactionService.withdrawMoney(accountId, amount);

            // Add additional logic if required (e.g., logging success or updating transaction history)
            System.out.printf("Recurring payment successful for Account ID %d: Amount %.2f, Description: %s%n",
                    accountId, amount, description);
        } catch (Exception e) {
            // Handle exceptions, such as insufficient balance or account not found
            System.err.printf("Failed to process recurring payment for Account ID %d: %s%n", accountId, e.getMessage());
            throw new JobExecutionException("Error during recurring payment execution", e);
        }
    }

}
