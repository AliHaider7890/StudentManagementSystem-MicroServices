//package com.example.Course.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated() // sab requests authenticated hon
//                )
//                .oauth2ResourceServer(oauth -> oauth
//                        .jwt() // JWT ko read karega
//                );
//
//        return http.build();
//    }
//}