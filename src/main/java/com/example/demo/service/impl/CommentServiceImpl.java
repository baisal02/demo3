package com.example.demo.service.impl;

import com.example.demo.dto.CommentRequest;
import com.example.demo.dto.CommentResponse;
import com.example.demo.dto.SimpleResponse;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Like;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.repositories.CommentRepo;
import com.example.demo.repositories.PostRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service

public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final PostRepo postRepo;

    public CommentServiceImpl(CommentRepo commentRepo, UserRepo userRepo, PostRepo postRepo) {
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @Override
    public SimpleResponse commentPost(Long postId, CommentRequest commentRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Post post = postRepo.findById(postId).orElseThrow(() -> new UsernameNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setComment(commentRequest.getComment());
        comment.setCreatedAt(LocalDate.now());

        if(user.getComments()!=null) {
            comment.setUser(user);
            user.getComments().add(comment);
            commentRepo.save(comment);
            userRepo.save(user);
        }

        if(post.getComments()!=null) {
            comment.setPost(post);
            post.getComments().add(comment);
            commentRepo.save(comment);
            postRepo.save(post);
        }

        return new SimpleResponse("Comment sent", HttpStatus.OK);
    }

    @Override
    public SimpleResponse deleteComment(Long commentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Comment currentUsersComment = commentRepo.findById(commentId).orElseThrow(()-> new UsernameNotFoundException("Comment not found"));

        if(user.getComments()!=null) {
            if(user.getComments().contains(currentUsersComment)) {
                //user
                user.getComments().remove(currentUsersComment);
                currentUsersComment.setUser(null);
                //post
                if(currentUsersComment.getPost()!=null) {
                    Post post = currentUsersComment.getPost();
                    post.getComments().remove(currentUsersComment);
                    currentUsersComment.setPost(null);
                }

                if(currentUsersComment.getLikes() != null) {
                    List<Like> likes = currentUsersComment.getLikes();
                    for(Like like : likes) {
                        if(like.getUser()!=null) {
                            like.getUser().getLikes().remove(like);
                            like.setUser(null);
                        }
                    }
                }
                commentRepo.delete(currentUsersComment);
                userRepo.save(user);
                return  new SimpleResponse("Comment deleted", HttpStatus.OK);
            }
        }
        return new SimpleResponse("Failed", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<CommentResponse> getCommentsOfPost(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new UsernameNotFoundException("Post not found"));
        List<CommentResponse>commentResponses = new ArrayList<>();
        if(post.getComments()!=null) {
        List<Comment> comments = post.getComments();
        for(Comment comment : comments) {
            commentResponses.add(new CommentResponse(
                    comment.getId(),
                    comment.getComment(),
                    comment.getCreatedAt()
            ));
        }
        }
        return commentResponses;
    }
}
