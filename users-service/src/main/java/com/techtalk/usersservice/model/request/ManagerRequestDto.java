package com.techtalk.usersservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder @Data @AllArgsConstructor
public class ManagerRequestDto {
    private String email;
    private String name;
    private String password;
    private Long phoneNumber;
}
