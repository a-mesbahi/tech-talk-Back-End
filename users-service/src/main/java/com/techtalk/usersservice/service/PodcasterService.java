package com.techtalk.usersservice.service;

import com.techtalk.usersservice.model.request.PodcasterRequestDto;
import com.techtalk.usersservice.model.response.PodcasterResponseDto;
import com.techtalk.usersservice.persistence.Podcaster;

import java.util.List;
import java.util.Optional;

public interface PodcasterService {
    PodcasterResponseDto register(PodcasterRequestDto podcasterRequestDto);
    Optional<Podcaster> loadPodcasterByEmail(String email);

    PodcasterResponseDto getPodcasterByEMail(String email);
    PodcasterResponseDto getPodcasterById(Long id);
    List<PodcasterResponseDto> getPodcasterByNameContains(String key);
    List<PodcasterResponseDto> getAllPodcasters(int page, int size, Integer verified);
    int verifiedMultiplePodcasters(int newVerified, List<Long> ids);

    int updateOwnProfile(PodcasterRequestDto podcasterRequestDto,String email );

}
