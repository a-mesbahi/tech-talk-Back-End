package com.example.podcastservice.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PodcastResponseDto {
    private Long id;
    private String audio;
    private String pic;
    private String title;
    private String description;
    private String podcaster_name;
    private Double podcast_duration;
    private int status;
    private Podcaster podcaster;
    private LocalDate date;
    private String rejectedMessage;
}
