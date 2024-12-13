package com.example.Bank_System.controller;

import com.example.Bank_System.request.CustomerRequest;
import com.example.Bank_System.request.KYCUpdateRequest;
import com.example.Bank_System.response.CustomerResponse;
import com.example.Bank_System.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }

    // Register a new customer
    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.registerCustomer(request);
        return ResponseEntity.ok(response);
    }

    // Update KYC details
    @PutMapping("/{customerId}/kyc")
    public ResponseEntity<CustomerResponse> updateKYCDetails(@PathVariable Long customerId, @RequestBody KYCUpdateRequest request) throws AccessDeniedException {
//        validateCustomerAccess(customerId);
        CustomerResponse response = customerService.updateKYCDetails(customerId, request);
        return ResponseEntity.ok(response);
    }

    // Get customer profile
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerProfile(@PathVariable Long customerId) throws AccessDeniedException {
//        validateCustomerAccess(customerId);
        CustomerResponse response = customerService.getCustomerProfile(customerId);
        return ResponseEntity.ok(response);
    }

    // Deactivate customer account
    @PutMapping("/{customerId}/deactivate")
    public ResponseEntity<String> deactivateCustomerAccount(@PathVariable Long customerId) throws AccessDeniedException {
//        validateCustomerAccess(customerId);
        String message = customerService.deactivateCustomerAccount(customerId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{customerId}/activate")
    public ResponseEntity<String> activateCustomerAccount(@PathVariable Long customerId) throws AccessDeniedException {
//        validateCustomerAccess(customerId);
        String message = customerService.activateCustomerAccount(customerId);
        return ResponseEntity.ok(message);
    }

    // Validate that the authenticated user can access the requested customer ID
    private void validateCustomerAccess(Long customerId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long authenticatedCustomerId = Long.parseLong(authentication.getName()); // Assuming user ID is stored as the username
        if (!authenticatedCustomerId.equals(customerId)) {
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }
    }
}
