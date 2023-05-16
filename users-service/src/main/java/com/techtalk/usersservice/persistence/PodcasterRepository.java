package com.techtalk.usersservice.persistence;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface PodcasterRepository extends JpaRepository<Podcaster,Long> {

    Optional<Podcaster> findPodcasterByEmail(String email);

    List<Podcaster> findPodcasterByNameContainingIgnoreCase(String name);

    @Override
    Page<Podcaster> findAll(Pageable pageable);

    Page<Podcaster> findByVerified(int verified,Pageable pageable);

    @Modifying
    @Query("UPDATE Podcaster p SET p.verified= :newVerified WHERE p.id IN :ids")
    int updateVerifiedForPodcaster(@Param("newVerified") int newVerified, @Param("ids") List<Long> ids);
}
