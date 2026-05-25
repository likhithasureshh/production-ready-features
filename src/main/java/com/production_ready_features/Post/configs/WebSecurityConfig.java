package com.production_ready_features.Post.configs;

import com.production_ready_features.Post.entities.enums.Role;
import com.production_ready_features.Post.filter.JwtAuthFilter;
import com.production_ready_features.Post.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

import static com.production_ready_features.Post.entities.enums.Permission.*;
import static com.production_ready_features.Post.entities.enums.Role.ADMIN;
import static com.production_ready_features.Post.entities.enums.Role.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final String[] publicRoutes = {
            "/auth/**","/login","/home.html"
    };


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers(HttpMethod.GET,"/posts/**").hasAuthority(POST_VIEW.name())
                        .requestMatchers(HttpMethod.POST,"/posts").hasAuthority(POST_CREATE.name())
                        .requestMatchers(HttpMethod.PUT,"/posts/**").hasAuthority(POST_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE,"/posts/**").hasAuthority(POST_DELETE.name())

//
                        .anyRequest().authenticated())
                .csrf(csrfConfig->csrfConfig.disable())
//                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .oauth2Login(oauth2Config -> oauth2Config
//                        .failureUrl("http://localhost:9000/login?error=true")
//                        .successHandler(oAuth2SuccessHandler));
//                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

//    @Bean
//    UserDetailsService userDetailsService()
//    {
//        UserDetails userDetails1 = User
//                .withUsername("Likitha")
//                .password(passwordEncoder().encode("Likitha@123"))
//                .roles("USER")
//                .build();
//        UserDetails userDetails2 = User
//                .withUsername("guddu")
//                .password(passwordEncoder().encode("guddu@123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(List.of(userDetails1,userDetails2));
//
//    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
    {
        return configuration.getAuthenticationManager();
    }
}
