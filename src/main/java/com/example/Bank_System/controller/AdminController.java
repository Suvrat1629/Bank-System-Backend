package com.example.Bank_System.controller;

import com.example.Bank_System.model.Admin;
import com.example.Bank_System.request.AdminRequest;
import com.example.Bank_System.response.AdminResponse;
import com.example.Bank_System.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(AdminService adminService, BCryptPasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }

    // Create Admin
    @PostMapping("/registerAdmin")
    public ResponseEntity<AdminResponse> createAdmin(@RequestBody AdminRequest adminRequest) {
        AdminResponse createdAdmin = adminService.createAdmin(adminRequest);
        return ResponseEntity.ok(createdAdmin);
    }

    // Get All Admins
    @GetMapping
    public ResponseEntity<List<AdminResponse>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    // Get Admin by ID
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }
}
