package com.example.podcastservice.persistence;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Data @Builder
@Entity public class Podcast {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String audio;
    private String pic;
    private String title;
    private String podcaster_name;
    private Double podcast_duration;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Long episode_id;
    private Long podcasterId;
    private Integer status=0;
    private LocalDate date = LocalDate.now();
    private String rejectedMessage;
}
