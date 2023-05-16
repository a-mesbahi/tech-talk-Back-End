package com.techtalk.usersservice.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findAdminByEmail(String email);
}
