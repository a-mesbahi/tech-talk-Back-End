package com.example.podcastservice.client;

import com.example.podcastservice.model.response.Podcaster;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "users-service")
public interface UserService {
    @RequestMapping(method = RequestMethod.POST, value = "/podcaster")
    Podcaster getUser(@RequestBody String email);

    @RequestMapping(method = RequestMethod.GET, value = "/podcaster/{id}")
    Podcaster getPodcasterById(@PathVariable("id") Long id);
}
