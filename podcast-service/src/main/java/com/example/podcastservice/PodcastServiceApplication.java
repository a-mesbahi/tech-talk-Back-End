package com.example.podcastservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PodcastServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PodcastServiceApplication.class, args);
    }

}
