package com.production_ready_features.Post;

import com.production_ready_features.Post.entities.User;
import com.production_ready_features.Post.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTests {
    @Autowired
    private JwtService jwtService;
    @Test
    void generateToken()
    {
        User user = new User(3L,"likitha","liki@gmail.com","1234");
        String token = jwtService.generateToken(user);
        System.out.println(token);

    }

    @Test
    void getUserFromToken()
    {

        Long id = jwtService.getUserIdFromToken("eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIzIiwiZW1haWwiOiJsaWtpQGdtYWlsLmNvbSIsInJvbGVzIjpbIkFETUlOIiwiVVNFUiJdLCJpYXQiOjE3NzkwMjc0ODMsImV4cCI6MTc3OTAyNzU0M30.NRIDoW-6wh1o2Ek5mARlEPbbhfffv-ZClUY_WqWjEZjbWxa0VDNiwmxSX0lqvYRZ");
        System.out.println(id);
    }


}
