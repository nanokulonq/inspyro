package com.nanokulon.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/ideas/*/edit").hasRole("MANAGER")
                        .requestMatchers("/ideas/*/delete").hasRole("MANAGER")
                        .requestMatchers("/login").not().authenticated()
                        .requestMatchers("/ideas/create").authenticated()
                        .requestMatchers("/", "/**", "/css/**", "/js/**").permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/ideas")
                        .permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/")
                        .permitAll())
                .build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
