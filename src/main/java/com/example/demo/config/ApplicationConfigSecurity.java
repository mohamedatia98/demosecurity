
// package com.example.demo.config;

// import javax.sql.DataSource;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.provisioning.JdbcUserDetailsManager;
// import org.springframework.security.provisioning.UserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;

// import jakarta.annotation.PostConstruct;

// @Configuration
// public class ApplicationConfigSecurity {

//         @PostConstruct
//         public void testUsers() {
//                 System.out.println("✅ InMemory users loaded successfully!");
//         }

//         // 1) Password encoder (bcrypt)
//         @Bean
//         public PasswordEncoder passwordEncoder() {
//                 return new BCryptPasswordEncoder();
//         }

//         // Custom Table JDBC
//         @Bean
//         public UserDetailsManager userDetailsManager(DataSource dataSource) {
//                 JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

//                 // define query to retrieve a user by username
//                 jdbcUserDetailsManager.setUsersByUsernameQuery(
//                                 "select user_id, pw, active from members where user_id=?");

//                 // define query to retrieve the authorities/roles by username
//                 jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
//                                 "select user_id, roles from roles where user_id=?");

//                 return jdbcUserDetailsManager;
//         }

//         // add support for JDBC (default names of authorities - users tables ) . ....
//         // not hard coded . default names of tables

//         // @Bean
//         // public UserDetailsManager userDetailsManager(DataSource dataSource) {

//         // return new JdbcUserDetailsManager(dataSource);
//         // }

//         // hard coded Memory Security
//         // @Bean
//         // public InMemoryUserDetailsManager userDetailsManager() {

//         // UserDetails ismial = User.builder()
//         // .username("ismail")
//         // .password("{noop}111")
//         // .roles("EMPLOYEE")
//         // .build();

//         // UserDetails moatia = User.builder()
//         // .username("moatia")
//         // .password(passwordEncoder().encode("111"))
//         // .roles("EMPLOYEE", "MANAGER")
//         // .build();

//         // UserDetails genius = User.builder()
//         // .username("genius")
//         // .password("{noop}111")
//         // .roles("EMPLOYEE", "MANAGER", "ADMIN")
//         // .build();
//         // return new InMemoryUserDetailsManager(ismial, moatia, genius);
//         // }

//         @Bean
//         public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//                 // the order of request matchers does matter !
//                 http
//                                 .authorizeHttpRequests(auth -> auth
//                                                 // Allow login page and static resources
//                                                 .requestMatchers("/login", "/error", "/css/**", "/js/**", "/images/**")
//                                                 .permitAll()

//                                                 // Protect API endpoints
//                                                 .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
//                                                 .requestMatchers("/api/**",
//                                                                 "/courses")
//                                                 .hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")

//                                                 // Anything else must be authenticated
//                                                 .anyRequest().authenticated())

//                                 // .formLogin(Customizer.withDefaults()) // default login form
//                                 .formLogin(form -> form.loginPage("/login").permitAll()
//                                                 .defaultSuccessUrl("/courses")
//                                                 .passwordParameter("password")
//                                                 .usernameParameter("username"))

//                                 // Remeber me
//                                 .rememberMe(remember -> remember
//                                                 .key("super-secret-key")
//                                                 .tokenValiditySeconds(2)
//                                                 .rememberMeParameter("remember-me"))

//                                 // logout
//                                 .logout(logout -> logout
//                                                 .logoutUrl("/logout")
//                                                 .logoutSuccessUrl("/login?logout")
//                                                 .deleteCookies("JSESSIONID", "remember-me")
//                                                 .invalidateHttpSession(true)
//                                                 .permitAll())

//                                 .httpBasic(Customizer.withDefaults()) // Postman support
//                                 .csrf(csrf -> csrf.disable());

//                 return http.build();
//         }

// }
