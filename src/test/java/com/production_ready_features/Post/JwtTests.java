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

    }

    @Test
    void getUserFromToken()
    {


    }


}
