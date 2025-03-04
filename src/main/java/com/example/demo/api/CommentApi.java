package com.example.demo.api;

import com.example.demo.dto.CommentRequest;
import com.example.demo.dto.CommentResponse;
import com.example.demo.dto.SimpleResponse;
import com.example.demo.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentApi {
        private final CommentService commentService;

    public CommentApi(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/choosePost/{postId}")
    public SimpleResponse postComment(@PathVariable Long postId, @RequestBody CommentRequest commentRequest){
       return commentService.commentPost(postId, commentRequest);
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public SimpleResponse deleteComment(@PathVariable Long commentId){
        return commentService.deleteComment(commentId);
    }

    @GetMapping("/getCommentsOfPost/{postId}")
    public List<CommentResponse> getCommentsOfPost(@PathVariable Long postId){
        return commentService.getCommentsOfPost(postId);
    }

}
