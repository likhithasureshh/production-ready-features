package com.production_ready_features.Post.service.impl;

import com.production_ready_features.Post.dtos.PostDto;
import com.production_ready_features.Post.entities.Post;
import com.production_ready_features.Post.exceptions.ResourceNotFoundException;
import com.production_ready_features.Post.repositories.PostRepository;
import com.production_ready_features.Post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<PostDto> getAllPosts() {
        List<Post> post = postRepository.findAll();
        return post.stream()
                .map(post1 -> modelMapper.map(post1,PostDto.class))
                .toList();

    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("the post with this id is not present"+postId));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostDto createNewPost(PostDto postDto) {
        Post post = modelMapper.map(postDto,Post.class);
        return modelMapper.map(postRepository.save(post), PostDto.class);
    }

    @Override
    public PostDto updatePosts(Long postId, PostDto postDto) {
        Post source = modelMapper.map(postDto,Post.class);
        source.setId(postId);

        Post target = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("post with this id is not found: "+postId));
        modelMapper.map(source,target);
        return modelMapper.map(postRepository.save(target),PostDto.class);
    }
}
