package com.example.Bank_System.service;

import com.example.Bank_System.request.AdminRequest;
import com.example.Bank_System.response.AdminResponse;
import com.example.Bank_System.model.Admin;
import com.example.Bank_System.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create Admin
    public AdminResponse createAdmin(AdminRequest adminRequest) {
        if (adminRepository.findByUsername(adminRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Admin with username " + adminRequest.getUsername() + " already exists.");
        }

        Admin admin = new Admin();
        admin.setUsername(adminRequest.getUsername());
        admin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
        admin.setRole("ADMIN");

        Admin savedAdmin = adminRepository.save(admin);
        return mapToResponse(savedAdmin);
    }


    // Get All Admins
    public List<AdminResponse> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get Admin by ID
    public AdminResponse getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + id));
        return mapToResponse(admin);
    }


    // Update Admin
    public AdminResponse updateAdmin(Long id, AdminRequest adminRequest) {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + id));

        existingAdmin.setUsername(adminRequest.getUsername());
        if (adminRequest.getPassword() != null && !adminRequest.getPassword().isEmpty()) {
            existingAdmin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
        }

        Admin updatedAdmin = adminRepository.save(existingAdmin);
        return mapToResponse(updatedAdmin);
    }

    // Delete Admin
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Admin not found with ID: " + id);
        }
        adminRepository.deleteById(id);
    }

//    @Override
//    public Admin getAdminByUsername(String username) {
//        return adminRepository.findByUsername(username)
//                .orElseThrow(()->new RuntimeException("Admin not found exception"));
//    }


    @Override
    public boolean isAdmin(String username) {
        return false;
    }

    // Map Admin to AdminResponse
    private AdminResponse mapToResponse(Admin admin) {
        AdminResponse response = new AdminResponse();
        response.setId(admin.getId());
        response.setUsername(admin.getUsername());
        response.setRole(admin.getRole());
        return response;
    }
}
