package com.production_ready_features.Post.dtos;

import com.production_ready_features.Post.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long postId;
    private String title;
    private String description;
    private UserDto owner;
}
