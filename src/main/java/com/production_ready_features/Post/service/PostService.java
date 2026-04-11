package com.production_ready_features.Post.service;

import com.production_ready_features.Post.dtos.PostDto;
import com.production_ready_features.Post.entities.Post;

import java.util.List;

public interface PostService {

    public List<PostDto> getAllPosts();
    public PostDto getPostById(Long postId);
    public PostDto createNewPost(PostDto postDto);

    PostDto updatePosts(Long postId, PostDto postDto);
}
