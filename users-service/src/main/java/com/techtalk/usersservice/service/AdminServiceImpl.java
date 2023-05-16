package com.techtalk.usersservice.service;

import com.techtalk.usersservice.model.request.AdminRequestDto;
import com.techtalk.usersservice.persistence.Admin;
import com.techtalk.usersservice.persistence.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Service public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;
    private PodcasterService podcasterService;

    public AdminServiceImpl(AdminRepository adminRepository, PodcasterService podcasterService) {
        this.adminRepository = adminRepository;
        this.podcasterService = podcasterService;
    }

    @Override
    public Admin addAdmin(AdminRequestDto adminRequestDto) {
        return null;
    }

    @Override
    public Optional<Admin> loadAdminByEmail(String email) {
        return this.adminRepository.findAdminByEmail(email);
    }


}
