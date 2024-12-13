package com.example.Bank_System.service;

import com.example.Bank_System.config.JwtProvider;
import com.example.Bank_System.exception.CustomException;
import com.example.Bank_System.model.Account;
import com.example.Bank_System.model.Customer;
import com.example.Bank_System.repository.CustomerRepository;
import com.example.Bank_System.request.CustomerRequest;
import com.example.Bank_System.request.KYCUpdateRequest;
import com.example.Bank_System.response.CustomerResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponse registerCustomer(CustomerRequest request) {
        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("Customer with the given email already exists");
        }

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setIdProof(request.getIdProof());

        // Hash the password before saving
        customer.setPassword(passwordEncoder.encode(request.getPassword()));

        Customer savedCustomer = customerRepository.save(customer);
        return mapToResponse(savedCustomer);
    }

    // Update KYC details
    public CustomerResponse updateKYCDetails(Long customerId, KYCUpdateRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException("Customer not found"));

        customer.setIdProof(request.getIdProof());
        customer.setAddress(request.getAddress());

        Customer updatedCustomer = customerRepository.save(customer);
        return mapToResponse(updatedCustomer);
    }

    // Get customer profile
    public CustomerResponse getCustomerProfile(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException("Customer not found"));

        return mapToResponse(customer);
    }

    // Deactivate a customer account
    public String deactivateCustomerAccount(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException("Customer not found"));

        if (!customer.isActive()) {
            return "Customer account is already inactive";
        }

        customer.setActive(false);
        customerRepository.save(customer);
        return "Customer account has been deactivated";
    }


    // Activate a customer account
    @Override
    public String activateCustomerAccount(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomException("Customer not found"));

        if (customer.isActive()) {
            return "Customer account is already active";
        }

        customer.setActive(true);
        customerRepository.save(customer);
        return "Customer account has been activated";
    }

    // Helper method to map Customer to CustomerResponse
    private CustomerResponse mapToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setAddress(customer.getAddress());
        response.setActive(customer.isActive());

        if (customer.getAccounts() != null) {
            response.setAccountIds(
                    customer.getAccounts()
                            .stream()
                            .map(Account::getId)
                            .collect(Collectors.toList())
            );
        }

        return response;
    }
}