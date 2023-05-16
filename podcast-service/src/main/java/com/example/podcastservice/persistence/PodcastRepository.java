package com.example.podcastservice.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PodcastRepository extends JpaRepository<Podcast, Long> {

    Page<Podcast> findByStatus(int status, Pageable pageable);

    Page<Podcast> findByPodcasterIdAndStatusNot(Long id,int status,Pageable pageable);

    Page<Podcast> findPodcastByPodcasterIdAndStatus(Long id,int status,Pageable pageable);

    int countByStatus(int status);

}
