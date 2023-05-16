package com.example.podcastservice.service;

import com.example.podcastservice.model.request.PodcasterRequestDto;
import com.example.podcastservice.model.request.UpdateStatus;
import com.example.podcastservice.model.response.PodcastResponseDto;


import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

public interface PodcastService {
    PodcastResponseDto addPodcast(PodcasterRequestDto podcasterRequestDto,String email);

    List<PodcastResponseDto> getPodcastsWithPagination(int page, int size,int status);

    PodcastResponseDto getSinglePodcast(Long id);

    int updatePodcastStatus(UpdateStatus updateStatus);

    List<PodcastResponseDto> getOnwPodcasts(String email,int page, int size);

    PodcastResponseDto updatePodcast(PodcasterRequestDto podcasterRequestDto, Long id);

    List<PodcastResponseDto> getPodcasterPodcasts(Long podcasterId, int page, int size );

}
