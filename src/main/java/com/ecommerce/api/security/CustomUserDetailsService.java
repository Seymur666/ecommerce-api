package com.ecommerce.api.security;

import com.ecommerce.api.persistence.entity.User;
import com.ecommerce.api.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

//        return new InMemoryUserDetailsManager(
//                User.builder()
//                        .username("Seymur")
//                        .password(passwordEncoder().encode("Seymur123"))
//                        .roles("USER")
//                        .build()
//        );

        return new CustomUserDetails(user);
    }
}
