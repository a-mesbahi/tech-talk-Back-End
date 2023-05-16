package com.techtalk.usersservice.authentication;

import org.springframework.security.core.userdetails.User;

public interface UserAuthenticationStrategy {
    User authenticate(String email);

}
