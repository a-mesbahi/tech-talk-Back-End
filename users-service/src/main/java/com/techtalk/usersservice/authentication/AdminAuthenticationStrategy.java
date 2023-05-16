package com.techtalk.usersservice.authentication;

import com.techtalk.usersservice.persistence.Admin;
import com.techtalk.usersservice.service.AdminService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class AdminAuthenticationStrategy implements UserAuthenticationStrategy{


    private AdminService adminService;
    private GrantedAuthority authority;
    private List<GrantedAuthority> authorities;

    public AdminAuthenticationStrategy(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public User authenticate(String email) {
        Optional<Admin> admin = this.adminService.loadAdminByEmail(email);
        if (admin.isPresent()) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
            List<GrantedAuthority> authorities = Collections.singletonList(authority);
            return new User(admin.get().getEmail(), admin.get().getPassword(), authorities);
        } else {
            return null; // User not found in the Admin table
        }
    }
}
