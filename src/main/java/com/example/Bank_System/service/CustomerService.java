package com.example.Bank_System.service;

import com.example.Bank_System.request.CustomerRequest;
import com.example.Bank_System.request.KYCUpdateRequest;
import com.example.Bank_System.response.CustomerResponse;

public interface CustomerService {

    public CustomerResponse registerCustomer(CustomerRequest request);
    public CustomerResponse updateKYCDetails(Long customerId, KYCUpdateRequest request);
    public CustomerResponse getCustomerProfile(Long customerId);
    public String deactivateCustomerAccount(Long customerId);
    public String activateCustomerAccount(Long customerId);
}
