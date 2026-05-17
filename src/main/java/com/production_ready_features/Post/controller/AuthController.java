package com.production_ready_features.Post.controller;

import com.production_ready_features.Post.dtos.LoginDto;
import com.production_ready_features.Post.dtos.SignUpRequest;
import com.production_ready_features.Post.dtos.UserDto;
import com.production_ready_features.Post.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequest signUpRequest)
    {
        UserDto userDto = authService.signUp(signUpRequest);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse httpServletResponse)
    {
        String token = authService.login(loginDto);
        Cookie cookie = new Cookie("token",token);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(token);
    }
}
