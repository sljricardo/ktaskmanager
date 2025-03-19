package com.sljricardo.tkmanager.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Disable CSRF for API usage
            .authorizeHttpRequests {
                it.requestMatchers("/task/all", "/task/search").permitAll() // Allow public access
                it.requestMatchers("/task/**").authenticated() // Protect specific endpoints
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // No sessions
            .httpBasic { } // Enable basic authentication for testing

        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user: UserDetails = User.builder()
            .username("admin")
            .password("{noop}password") // {noop} means plain text (change to bcrypt in production)
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }
}
