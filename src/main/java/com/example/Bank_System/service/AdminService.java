package com.example.Bank_System.service;

import com.example.Bank_System.model.Admin;
import com.example.Bank_System.request.AdminRequest;
import com.example.Bank_System.response.AdminResponse;

import java.util.List;

public interface AdminService {

    public AdminResponse createAdmin(AdminRequest adminRequest);
    public List<AdminResponse> getAllAdmins();
    public AdminResponse getAdminById(Long id);
    public AdminResponse updateAdmin(Long id, AdminRequest adminRequest);
    public void deleteAdmin(Long id);
//    public Admin getAdminByUsername(String username);
    public boolean isAdmin(String username);
}
