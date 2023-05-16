package com.techtalk.usersservice.security;


import com.techtalk.usersservice.authentication.UserAuthenticationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private PasswordEncoder passwordEncoder;
    private List<UserAuthenticationStrategy> userAuthenticationStrategies;

    public SecurityConfig(PasswordEncoder passwordEncoder, List<UserAuthenticationStrategy> userAuthenticationStrategies) {
        this.passwordEncoder = passwordEncoder;
        this.userAuthenticationStrategies = userAuthenticationStrategies;
    }

    @Bean
    UserDetailsService userDetailsService() throws Exception{
        return email -> {
            String newEmail = email.split(":")[0];
            String user = email.split(":")[1];

            for (UserAuthenticationStrategy strategy : userAuthenticationStrategies) {
                User userAuth = strategy.authenticate(newEmail);
                if(userAuth!=null){
                    return userAuth;
                }
            }
            throw new BadCredentialsException("error!");
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable());
        http
                //.authorizeHttpRequests(au->au.anyRequest().permitAll())
                .authorizeHttpRequests(aut->aut.requestMatchers("/actuator/**").permitAll())
                .authorizeHttpRequests(aut->aut.requestMatchers(HttpMethod.POST,"/podcaster").permitAll())
                .authorizeHttpRequests(aut->aut.requestMatchers("/podcaster/**").permitAll())
                .authorizeHttpRequests(aut->aut.requestMatchers("/podcasters").permitAll())
                .authorizeHttpRequests(aut->aut.requestMatchers("/podcasters/**").permitAll())
                .authorizeHttpRequests(aut->aut.requestMatchers("/podcaster/me").authenticated())
                .authorizeHttpRequests(auth->auth.anyRequest().authenticated())
                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());
        http.addFilter(new JwtAuthenticationFilter(authenticationManager(this.authenticationProvider(this.userDetailsService()))));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
        return new ProviderManager(Arrays.asList(authenticationProvider));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(this.passwordEncoder);
        authenticationProvider.setHideUserNotFoundExceptions(true);
        return authenticationProvider;
    }




}
