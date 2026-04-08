package com.production_ready_features.Post.controller;

import com.production_ready_features.Post.dtos.PostDto;
import com.production_ready_features.Post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/post")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts()
    {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping(path = "/{postId}")
    public ResponseEntity<PostDto> getPostsById(@PathVariable Long postId)
    {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PostMapping
    public ResponseEntity<PostDto> createNewPosts(@RequestBody PostDto postDto)
    {
        return ResponseEntity.ok(postService.createNewPost(postDto));
    }
}
