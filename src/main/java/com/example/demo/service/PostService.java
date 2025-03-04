package com.example.demo.service;

import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.dto.PostUpdateRequest;
import com.example.demo.dto.SimpleResponse;
import com.example.demo.entities.Post;

import java.util.List;

public interface PostService {
    SimpleResponse createPost(PostRequest postRequest);
    SimpleResponse updatePost(Long postId, PostUpdateRequest postUpdateRequest);
    PostResponse getPost(Long id);
    List<PostResponse> getOwnPostAndSubscriptionsPost();

    SimpleResponse tagPersonInPost(Long userId,Long postId,Long imageId);
}
