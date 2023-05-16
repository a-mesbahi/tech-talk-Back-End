package com.example.podcastservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data @AllArgsConstructor @Builder
public class UpdateStatus {
    private Long id;
    private int status;
    private String rejectedMessage;
}
