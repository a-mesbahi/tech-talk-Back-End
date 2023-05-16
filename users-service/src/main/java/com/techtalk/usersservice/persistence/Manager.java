package com.techtalk.usersservice.persistence;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity public class Manager extends User{
    private Long phoneNumber;
}
