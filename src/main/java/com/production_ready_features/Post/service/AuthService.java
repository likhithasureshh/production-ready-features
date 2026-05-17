package com.production_ready_features.Post.service;

import com.production_ready_features.Post.dtos.LoginDto;
import com.production_ready_features.Post.dtos.SignUpRequest;
import com.production_ready_features.Post.dtos.UserDto;
import com.production_ready_features.Post.entities.User;
import com.production_ready_features.Post.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserDto signUp(SignUpRequest signUpRequest) {
        Optional<User> user = userRepository.findByEmail(signUpRequest.getEmail());
        if(user.isPresent())
        {
            throw new BadCredentialsException("User already exists");
        }
        User toBeSaved = modelMapper.map(signUpRequest,User.class);
        toBeSaved.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return modelMapper.map(userRepository.save(toBeSaved),UserDto.class);
    }

    public String login(LoginDto loginDto)
    {
       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
       );
       User user = (User) authentication.getPrincipal();
        return jwtService.generateToken(user);
    }
}
