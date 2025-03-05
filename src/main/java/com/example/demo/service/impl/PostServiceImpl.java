package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.entities.Image;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.exceptions.MyNotFoundException;
import com.example.demo.repositories.ImageRepo;
import com.example.demo.repositories.PostRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final ImageRepo imageRepo;

    public PostServiceImpl(UserRepo userRepo, PostRepo postRepo, ImageRepo imageRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.imageRepo = imageRepo;
    }

    @Transactional
    @Override
    public SimpleResponse createPost(PostRequest postRequest) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findUserByEmail(currentEmail) // CURRENT USER
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = new Post();   //post to be created by user (which is using the app)
        List<Image> images = new ArrayList<>();
        if (postRequest.getImageRequestList() != null) {
            for (ImageRequest imageRequest : postRequest.getImageRequestList()) {
                images.add(new Image(
                        imageRequest.getUrl()
                ));
            }
        } else {
            return new SimpleResponse("Post can't be created without Image", HttpStatus.BAD_REQUEST);
        }
        imageRepo.saveAll(images);
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setCreatedAt(LocalDate.now());
        post.setImages(images);
        for (Image image : images) {
            image.setPost(post);
        }
        postRepo.save(post);

        user.getPosts().add(post);
        post.setUser(user);

        postRepo.save(post);
        userRepo.save(user);
        return new SimpleResponse("Post created", HttpStatus.CREATED);
    }

    @Override
    public SimpleResponse updatePost(Long postId, PostUpdateRequest postRequest) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findUserByEmail(currentEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new MyNotFoundException("Post not found"));
        if (currentUser.getPosts() != null) {
            if (currentUser.getPosts().contains(post)) {
                post.setTitle(postRequest.getTitle());
                post.setDescription(postRequest.getDescription());

                postRepo.save(post);// na vsakiy sluchai
                userRepo.save(currentUser);
                return new SimpleResponse("Post updated", HttpStatus.OK);
            } else {
                return new SimpleResponse("Post is not yours", HttpStatus.BAD_REQUEST);
            }
        }
        return new SimpleResponse("Something went wrong", HttpStatus.BAD_REQUEST);
    }

    @Override
    public PostResponse getPost(Long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new MyNotFoundException("Post not found"));
        PostResponse postResponse = new PostResponse();
        List<ImageResponse> imageResponses = new ArrayList<>();
        if (post.getImages() != null) {
            for (Image image : post.getImages()) {
                imageResponses.add(new ImageResponse(
                        image.getImageUrl()
                ));
            }
            postResponse.setImages(imageResponses);
        }
        postResponse.setTitle(post.getTitle());
        postResponse.setDescription(post.getDescription());
        postResponse.setCreationDate(post.getCreatedAt());
        return postResponse;
    }

    @Override
    public List<PostResponse> getOwnPostAndSubscriptionsPost() {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findUserByEmail(currentEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<PostResponse> postResponses = new ArrayList<>();
        List<Long> subscriptionsIDies = new ArrayList<>();
        if (user.getFollower() != null) {
            if (user.getFollower().getSubscriptions() != null) {
                subscriptionsIDies = user.getFollower().getSubscriptions();
            }
        }
        List<User> subscriptions = userRepo.findAllById(subscriptionsIDies);

        if (user.getPosts() != null) {
            for (Post post : user.getPosts()) {
                List<ImageResponse> imageResponses = new ArrayList<>();
                if (post.getImages() != null) {
                    for (Image image : post.getImages()) {
                        imageResponses.add(new ImageResponse(image.getId(), image.getImageUrl()));
                    }
                }
                postResponses.add(new PostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getDescription(),
                        imageResponses,
                        post.getCreatedAt()
                ));
            }
        }
        if (subscriptions != null) {
            for (User u : subscriptions) {
                if (u.getPosts() != null) {
                    for (Post post : u.getPosts()) {
                        List<ImageResponse> imageResponses = new ArrayList<>();
                        if (post.getImages() != null) {
                            for (Image image : post.getImages()) {
                                imageResponses.add(new ImageResponse(image.getId(), image.getImageUrl()));
                            }
                        }
                        postResponses.add(new PostResponse(
                                post.getId(),
                                post.getTitle(),
                                post.getDescription(),
                                imageResponses,
                                post.getCreatedAt()
                        ));
                    }
                }
            }
        }
        List<PostResponse> finalResponse = postResponses.stream()
                .sorted(Comparator.comparing(PostResponse::getCreationDate).reversed())
                .collect(Collectors.toList());
        return finalResponse;
    }
    @Transactional
    @Override
    public SimpleResponse tagPersonInPost(Long userId, Long postId, Long imageId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User me = userRepo.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Authenticated user not found"));

        User userToTag = userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User to tag not found"));
        if(me.getPosts() != null) {
        for (Post post : me.getPosts()) {
            if (post.getId()==postId) {
                if(post.getImages() != null) {
                for (Image image : post.getImages()) {
                    if (image.getId()==imageId) {

                            userToTag.setImage(image);
                            image.setUser(userToTag);

                            userRepo.save(userToTag);

                            return new SimpleResponse("The User has been tagged", HttpStatus.OK);
                        }

                        return new SimpleResponse("Tagging not possible", HttpStatus.BAD_REQUEST);
                    }
                }
                }
                return new SimpleResponse("Image not found in the post", HttpStatus.BAD_REQUEST);
            }
        }
        return new SimpleResponse("Post not found for the current user", HttpStatus.BAD_REQUEST);
    }

}

