package com.example.podcastservice.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Podcaster {
    private Long id;
    private String email;
    private String name;
    private Long phoneNumber;
    private String bio;
    private String profilePic;
    private String linkedinProfile;
    private Boolean verified;
}
