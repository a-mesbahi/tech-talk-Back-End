package com.techtalk.usersservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Builder @Data @AllArgsConstructor
public class PodcasterRequestDto {
    private String email;
    private String name;
    private String password;
    private Long phoneNumber;
    private String bio;
    private MultipartFile pic;
    private String linkedinProfile;
}
