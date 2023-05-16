package com.techtalk.usersservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder @AllArgsConstructor
public class PodcasterResponseDto {
    private Long id;
    private String email;
    private String name;
    private Long phoneNumber;
    private String bio;
    private String profilePic;
    private String linkedinProfile;
    private int verified ;
}
