package com.production_ready_features.Post.service;

import com.production_ready_features.Post.entities.User;
import com.production_ready_features.Post.exceptions.ResourceNotFoundException;
import com.production_ready_features.Post.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).
                orElseThrow(()-> new BadCredentialsException("User not found"));
    }

    public User findUserByUserId(Long userId)
    {
        return userRepository.findById(userId).orElseThrow(()-> new BadCredentialsException("User not found"));
    }

    public User findUserByUserEmail(String email)
    {
        return userRepository.findByEmail(email).orElse(null);
    }


    public User save(User newUser) {
        return userRepository.save(newUser);
    }
}
