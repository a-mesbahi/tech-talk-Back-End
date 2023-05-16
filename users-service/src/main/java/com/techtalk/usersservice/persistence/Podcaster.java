package com.techtalk.usersservice.persistence;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor
@Entity public class Podcaster extends User {
    private Long phoneNumber;
    private String bio;
    private String profilePic;
    private String linkedinProfile;
    private int verified=0;
}
