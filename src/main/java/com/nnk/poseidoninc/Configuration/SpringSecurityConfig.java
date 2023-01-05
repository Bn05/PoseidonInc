package com.nnk.poseidoninc.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public UserDetailsService users() {

        return new InMemoryUserDetailsManager(
                User.withUsername("user").password("{noop}password").authorities("USER").build(),
                User.withUsername("admin").password("{noop}password").authorities("USER", "ADMIN").build()
        );
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests().anyRequest().permitAll()
                .and()
                .build();
    }
}
