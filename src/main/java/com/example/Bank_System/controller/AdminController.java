package com.example.Bank_System.controller;

import com.example.Bank_System.request.AccountRequest;
import com.example.Bank_System.request.ManageInterestRequest;
import com.example.Bank_System.response.TransactionResponse;
import com.example.Bank_System.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/block-account")
    public ResponseEntity<String> blockAccount(@RequestBody AccountRequest accountRequest) {
        String message = adminService.blockAccount(accountRequest.getCustomerId());
        return ResponseEntity.ok(message);
    }

    @GetMapping("/monitor-transactions")
    public ResponseEntity<List<TransactionResponse>> monitorTransactions(@RequestBody BigDecimal threshold) {
        List<TransactionResponse> transactions = adminService.monitorTransactions(threshold);
        return ResponseEntity.ok(transactions);
    }

//    @PostMapping("/approve-account")
//    public ResponseEntity<String> approveAccountCreation(@RequestParam Long accountId) {
//        String message = adminService.approveAccountCreation(accountId);
//        return ResponseEntity.ok(message);
//    }

    @PostMapping("/manage-interest")
    public ResponseEntity<String> manageInterestRates(@RequestBody ManageInterestRequest request) {
        String message = adminService.manageInterestRates(request.getAccountType(), request.getNewRate());
        return ResponseEntity.ok(message);
    }

//    @GetMapping("/view-complaints")
//    public ResponseEntity<List<Complaint>> viewCustomerComplaints() {
//        List<Complaint> complaints = adminService.viewCustomerComplaints();
//        return ResponseEntity.ok(complaints);
//    }
}