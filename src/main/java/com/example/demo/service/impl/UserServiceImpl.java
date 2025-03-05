package com.example.demo.service.impl;

import com.example.demo.config.jwt.JwtService;
import com.example.demo.dto.*;
import com.example.demo.entities.*;
import com.example.demo.exceptions.MyNotFoundException;
import com.example.demo.exceptions.UserExistException;
import com.example.demo.repositories.FollowerRepo;
import com.example.demo.repositories.UserInfoRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepo userInfoRepo;
    private final FollowerRepo followerRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, JwtService jwtService, PasswordEncoder passwordEncoder, UserInfoRepo userInfoRepo, FollowerRepo followerRepo) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userInfoRepo = userInfoRepo;
        this.followerRepo = followerRepo;
    }

    //-----------------------------------------------------------------------//
    @Override
    public AuthResponse registerInApp(UserRegisterRequest userRegister) {
        List<User>allUsers = userRepo.findAll();
        for (User user : allUsers) {
            if(user.ownGetName()==userRegister.getNickname()){
                throw  new UserExistException("Nickname already exists");
            }
        } for (User user : allUsers) {
            if(user.getEmail()==userRegister.getEmail()){
                throw  new UserExistException("email already exists");
            }
        }
        String fullname = userRegister.getFirstame()+" "+userRegister.getSurname();
        //-----------
        Follower follower = new Follower();//for creating User
        List<Long>subscriptions = new ArrayList<>();
        List<Long>subscribers = new ArrayList<>();
        follower.setSubscribers(subscribers);
        follower.setSubscriptions(subscriptions);
        followerRepo.save(follower);
        //------------

        User user = new User();
        user.setUsername(userRegister.getNickname());
        user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        user.setEmail(userRegister.getEmail());
        user.setPhoneNumber(userRegister.getPhonenumber());
        userRepo.save(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(fullname);
        userInfo.setGender(userRegister.getGender());
        userInfoRepo.save(userInfo);

        user.setUserInfo(userInfo);
        userInfo.setUser(user);
        user.setFollower(follower);
        follower.setUser(user);

        userRepo.save(user);

        return new AuthResponse(user.getId(),user.getEmail(),jwtService.generateToken(user));
    }


    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepo.findUserByEmail(loginRequest.
                getEmail()).
                orElseThrow(()->new MyNotFoundException("User not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw new RuntimeException("Wrong password");
        }
        return new AuthResponse(user.getId(),user.getEmail(),jwtService.generateToken(user));
    }



    @Override
    public UserProfile getOwnUserProfile() {
        UserProfile userProfile = new UserProfile();  // value to return

       String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
       User currentUser = userRepo.findUserByEmail(currentUserEmail)
               .orElseThrow(()->new MyNotFoundException("User not found"));

       List<PostResponse>postResponses = new ArrayList<>();
        userProfile.setNickname(currentUser.ownGetName());
       if(currentUser.getPosts()!=null){
           List<Post>posts = currentUser.getPosts();
           posts = posts.stream()
                   .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                   .toList();

           for(Post post:posts){
               List<ImageResponse>imageResponses = new ArrayList<>();
               if(post.getImages()!=null){
                   for (Image image : post.getImages()) {
                       imageResponses.add(new ImageResponse(
                               image.getId(),
                               image.getImageUrl()
                       ));
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
           userProfile.setPostsResponses(postResponses);
       }



       if(currentUser.getFollower()!=null){
           if(currentUser.getFollower().getSubscribers()!=null&&currentUser.getFollower().getSubscriptions()!=null){
               Follower follower = currentUser.getFollower();
                userProfile.setSubscribersNumber(follower.getSubscribers().size());
                userProfile.setSubscriptionsNumber(follower.getSubscriptions().size());
           }
       }

       if(currentUser.getUserInfo()!=null){
           userProfile.setFullName(currentUser.getUserInfo().getFullName());
           ImageResponse imageResponse = new ImageResponse();
           imageResponse.setImageUrl(currentUser.getUserInfo().getImageUrl());
           userProfile.setImageResponse(imageResponse);
       }

        return userProfile;
    }

    @Override
    public UserProfile getUserProfile(Long id) {
        UserProfile userProfile = new UserProfile();
        User user = userRepo.findById(id).orElseThrow(()->new MyNotFoundException("User not found"));

        List<PostResponse>postResponses = new ArrayList<>();
        userProfile.setNickname(user.ownGetName());

        if(user.getPosts()!=null){
            List<Post>posts = user.getPosts();
            posts = posts.stream()
                    .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                    .toList();

            for(Post post:posts){
                List<ImageResponse>imageResponses = new ArrayList<>();
                if(post.getImages()!=null){
                    for (Image image : post.getImages()) {
                        imageResponses.add(new ImageResponse(
                                image.getId(),
                                image.getImageUrl()
                        ));
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
            userProfile.setPostsResponses(postResponses);
        }

        if(user.getFollower()!=null){
            if(user.getFollower().getSubscribers()!=null&&user.getFollower().getSubscriptions()!=null){
                Follower follower = user.getFollower();
                userProfile.setSubscribersNumber(follower.getSubscribers().size());
                userProfile.setSubscriptionsNumber(follower.getSubscriptions().size());
            }
        }

        if(user.getUserInfo()!=null){
            userProfile.setFullName(user.getUserInfo().getFullName());
            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setImageUrl(user.getUserInfo().getImageUrl());
            userProfile.setImageResponse(imageResponse);
        }
        return userProfile;
    }

    @Override
    public SimpleResponse updateUser(UpdateUserRequest updateUserRequest) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findUserByEmail(currentUserEmail).orElseThrow(()->new MyNotFoundException("User not found"));


        if(currentUser.getUserInfo()!=null){
            currentUser.setUsername(updateUserRequest.getNickname());
            currentUser.setPhoneNumber(updateUserRequest.getPhoneNumber());
        currentUser.getUserInfo().setBiography(updateUserRequest.getBiography());
        currentUser.getUserInfo().setGender(updateUserRequest.getGender());
        userRepo.save(currentUser);
        return new SimpleResponse("updated successfully",HttpStatus.OK);
        }

            return new SimpleResponse("UserInfo is null", HttpStatus.BAD_REQUEST);
    }

    @Override
    public SimpleResponse deleteUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findUserByEmail(currentUserEmail).orElseThrow(()->new MyNotFoundException("User not found"));

        //relation breaking----------------
        if(currentUser.getPosts()!=null){
            for(Post post:currentUser.getPosts()){
                if(post.getComments()!=null){
                    for(Comment comment:post.getComments()){
                        if(comment.getUser()!=null){
                            comment.getUser().getComments().remove(comment);
                            comment.setUser(null);
                        }
                    }
                }
            }
        }


        if(currentUser.getPosts()!=null){
            for(Post post:currentUser.getPosts()){
                if(post.getImages()!=null){
                    for (Image image:post.getImages()){
                        if(image.getUser()!=null){
                            image.getUser().setImage(null);
                            image.setUser(null);
                        }
                    }
                }
            }
        }


        if(currentUser.getPosts()!=null){
        List<Post>posts = currentUser.getPosts();
        for(Post post:posts){
            if(post.getLikes()!=null){
                List<Like>likes = post.getLikes();
                for(Like like:likes){
                    like.setPost(null);
                    if(like.getUser()!=null){
                        like.getUser().getLikes().remove(like);
                        like.setUser(null);
                    }
                }
                post.getLikes().clear();
            }
        }
        }

        if(currentUser.getComments()!=null){
            List<Comment>comments = currentUser.getComments();
            for(Comment comment:comments){
                if(comment.getLikes()!=null){
                    for (Like like : comment.getLikes()) {
                        like.setUser(null);
                        if(like.getUser()!=null){
                            like.getUser().getComments().remove(like);
                        }
                    }
                }
                if(comment.getPost()!=null){
                        comment.getPost().getComments().remove(comment);
                        comment.setPost(null);
                }
            }
        }

        if(currentUser.getLikes()!=null){
            for (Like like : currentUser.getLikes()) {
                if(like.getComment()!=null){
                    like.getComment().getLikes().remove(like);
                    like.setComment(null);
                }
                if(like.getPost()!=null){
                    like.getPost().getLikes().remove(like);
                    like.setPost(null);
                }
            }
        }

        if(currentUser.getImage()!=null){
            currentUser.getImage().setUser(null);
            currentUser.setImage(null);
        }
         //---------------------------------


        userRepo.delete(currentUser);
        return new SimpleResponse("deleted successfully",HttpStatus.OK);
    }
    //-------------------------------------------------------------------------//



    //
    private void SomeMethod() {
        ProfileResponse profileResponse = jwtService.getProfile();
        SecurityContextHolder.getContext().getAuthentication().getName(); // returns email of current user
    }
}
