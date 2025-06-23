package com.ecommerce.api.config;

//import com.ecommerce.api.security.CustomUserDetailsService;

import com.ecommerce.api.persistence.repository.UserRepository;
import com.ecommerce.api.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
//@RequiredArgsConstructor
public class SecurityConfig {
//    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//
//        User user = new User("Seymur", "$2a$10$DRcEeEF1rF/52kjeZZF44OdlyGuqU56ACb7zZd9mnRn9vtoLUYyhO", List.of());
//
//        inMemoryUserDetailsManager.createUser(user);
//
//        return inMemoryUserDetailsManager;
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return customUserDetailsService;
//    }
}
