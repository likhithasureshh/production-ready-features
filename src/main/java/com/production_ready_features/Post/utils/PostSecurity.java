package com.production_ready_features.Post.utils;

import com.production_ready_features.Post.dtos.PostDto;
import com.production_ready_features.Post.entities.User;
import com.production_ready_features.Post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

    public Boolean getPostByOwner(Long postId)
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDto postDto = postService.getPostById(postId);
        return postDto.getOwner().getId().equals(user.getId());

    }
}
