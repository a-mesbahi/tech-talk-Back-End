package com.techtalk.usersservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @Builder
public class UpdateVerified {
    Integer newVerified;
    List<Long> ids;

}
