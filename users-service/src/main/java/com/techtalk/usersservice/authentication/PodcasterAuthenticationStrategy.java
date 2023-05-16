package com.techtalk.usersservice.authentication;

import com.techtalk.usersservice.persistence.Podcaster;
import com.techtalk.usersservice.service.PodcasterService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PodcasterAuthenticationStrategy implements UserAuthenticationStrategy{


    private PodcasterService podcasterService;
    private GrantedAuthority authority;
    private List<GrantedAuthority> authorities;


    public PodcasterAuthenticationStrategy(PodcasterService podcasterService) {
        this.podcasterService = podcasterService;
    }

    @Override
    public User authenticate(String email) {
        Optional<Podcaster> podcaster = this.podcasterService.loadPodcasterByEmail(email);
        if (podcaster.isPresent()) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("PODCASTER");
            List<GrantedAuthority> authorities = Collections.singletonList(authority);
            return new User(podcaster.get().getEmail(), podcaster.get().getPassword(), authorities);
        } else {
            return null; // User not found in the Podcaster table
        }
    }
}
