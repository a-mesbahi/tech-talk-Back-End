package com.techtalk.usersservice.service;

import com.techtalk.usersservice.model.request.AdminRequestDto;
import com.techtalk.usersservice.persistence.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin addAdmin(AdminRequestDto adminRequestDto);
    Optional<Admin> loadAdminByEmail(String email);
}
