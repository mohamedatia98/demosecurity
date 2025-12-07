package com.example.demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // For demo purposes we return a single hard-coded user.
        // Replace with JPA repository call in real apps.
        if (!"mohamed".equals(username)) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return User.withUsername("mohamed")
                .password(new BCryptPasswordEncoder().encode("1234"))
                .authorities("USER_READ")
                .build();
    }
}
