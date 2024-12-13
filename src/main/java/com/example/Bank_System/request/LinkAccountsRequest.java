package com.example.Bank_System.request;

import java.util.List;

public class LinkAccountsRequest {

    private Long customerId;
    private List<Long> accountIds;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(List<Long> accountIds) {
        this.accountIds = accountIds;
    }
}
