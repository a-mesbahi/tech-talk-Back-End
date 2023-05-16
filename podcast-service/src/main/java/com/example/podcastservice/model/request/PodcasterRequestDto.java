package com.example.podcastservice.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PodcasterRequestDto {
    @Builder.Default
    private MultipartFile FileAudio = null;
    @Builder.Default
    private MultipartFile picture = null;
    private String title;
    private String podcaster_name;
    private String description;
    private Long episode_id;
    private Long podcasterId;
}
