package com.production_ready_features.Post.controller;

import com.production_ready_features.Post.dtos.LoginDto;
import com.production_ready_features.Post.dtos.LoginResponseDto;
import com.production_ready_features.Post.dtos.SignUpRequest;
import com.production_ready_features.Post.dtos.UserDto;
import com.production_ready_features.Post.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Value("${deploy.env}")
    private String deployEnv;
    private final AuthService authService;
    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequest signUpRequest)
    {
        UserDto userDto = authService.signUp(signUpRequest);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse httpServletResponse)
    {
        LoginResponseDto loginResponseDto = authService.login(loginDto);
        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(deployEnv.equals("production"));
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request)
    {
        String refreshToken =Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .map(cookie -> cookie.getValue())
                .findFirst()
                .orElseThrow(()-> new AuthenticationServiceException("Refresh Token not found!"));
     LoginResponseDto loginResponseDto =authService.refresh(refreshToken);
     return ResponseEntity.ok(loginResponseDto);


    }
}
