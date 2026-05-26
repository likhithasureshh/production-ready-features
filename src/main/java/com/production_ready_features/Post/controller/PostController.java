package com.production_ready_features.Post.controller;

import com.production_ready_features.Post.PostApplication;
import com.production_ready_features.Post.dtos.PostDto;
import com.production_ready_features.Post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts()
    {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping(path = "/{postId}")
    @PreAuthorize("@postSecurity.getPostByOwner(#postId)")
    public ResponseEntity<PostDto> getPostsById(@PathVariable Long postId)
    {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PostMapping
    @Secured({"ROLE_ADMIN","ROLE_CREATOR"})
    public ResponseEntity<PostDto> createNewPosts(@RequestBody PostDto postDto)
    {
        return ResponseEntity.ok(postService.createNewPost(postDto));
    }


    @PutMapping(path = "/{postId}")

    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId
    ,@RequestBody PostDto postDto)
    {
        return ResponseEntity.ok(postService.updatePosts(postId,postDto));
    }
}
