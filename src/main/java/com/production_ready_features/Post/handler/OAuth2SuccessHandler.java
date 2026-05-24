package com.production_ready_features.Post.handler;

import com.production_ready_features.Post.entities.User;
import com.production_ready_features.Post.service.JwtService;
import com.production_ready_features.Post.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User defaultOAuth2User= (DefaultOAuth2User) token.getPrincipal();

        String email = defaultOAuth2User.getAttribute("email");
        User user = userDetailsService.findUserByUserEmail(email);
        if(user==null)
        {
            User newUser = User.builder()
                    .name(defaultOAuth2User.getAttribute("name"))
                    .email(email)
                    .build();
            user = userDetailsService.save(newUser);

        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        String frontEndUrl = "http://localhost:9000/home.html?token="+accessToken;
        response.sendRedirect(frontEndUrl);

    }
}
