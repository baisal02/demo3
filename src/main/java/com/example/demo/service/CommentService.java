package com.example.demo.service;

import com.example.demo.dto.CommentRequest;
import com.example.demo.dto.CommentResponse;
import com.example.demo.dto.SimpleResponse;

import java.util.List;

public interface CommentService {

    SimpleResponse commentPost(Long postId, CommentRequest commentRequest);

    SimpleResponse deleteComment(Long commentId);

    List<CommentResponse> getCommentsOfPost(Long postId);

}
