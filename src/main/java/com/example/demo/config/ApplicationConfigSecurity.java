package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.PostConstruct;

@Configuration
public class ApplicationConfigSecurity {

    @PostConstruct
    public void testUsers() {
        System.out.println("✅ InMemory users loaded successfully!");
    }

    // 1) Password encoder (bcrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // hard coded Memory Security
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails ismial = User.builder()
                .username("ismail")
                .password("{noop}111")
                .roles("EMPLOYEE")
                .build();

        UserDetails moatia = User.builder()
                .username("moatia")
                .password(passwordEncoder().encode("111"))
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails genius = User.builder()
                .username("genius")
                .password("{noop}111")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(ismial, moatia, genius);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // the order of request matchers does matter !
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").hasAllRoles("EMPLOYEE", "MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults()) // Enables login form
                .httpBasic(Customizer.withDefaults()) // Enables basic auth popup (Postman)
                .csrf(csrf -> csrf.disable()); // Disable CSRF for dev/testing only

        return http.build();
    }

}
