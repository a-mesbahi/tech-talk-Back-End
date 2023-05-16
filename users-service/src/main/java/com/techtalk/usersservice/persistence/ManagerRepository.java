package com.techtalk.usersservice.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ManagerRepository extends JpaRepository<Manager,Long> {
    Manager findManagerByEmail(String email);
}
