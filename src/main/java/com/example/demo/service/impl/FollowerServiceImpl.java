package com.example.demo.service.impl;

import com.example.demo.dto.FollowResponse;
import com.example.demo.dto.FollowerFollowing;
import com.example.demo.dto.SubResponse;
import com.example.demo.entities.Follower;
import com.example.demo.entities.User;
import com.example.demo.repositories.CommentRepo;
import com.example.demo.repositories.FollowerRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.service.FollowerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowerServiceImpl implements FollowerService {
    private final UserRepo userRepo;
    private final CommentRepo commentRepo;
    private final FollowerRepo followerRepo;

    public FollowerServiceImpl(UserRepo userRepo, CommentRepo commentRepo, FollowerRepo followerRepo) {
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
        this.followerRepo = followerRepo;
    }

    @Override
    public FollowResponse follow(Long userIdToFollow) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("You don't exist"));
        User userfollow = userRepo.findById(userIdToFollow).orElseThrow(() -> new UsernameNotFoundException("The user not found"));

        if (user.getFollower() == null) {
            user.setFollower(new Follower());
        }
        if (userfollow.getFollower() == null) {
            userfollow.setFollower(new Follower());
        }

        // Initialize subscriptions and subscribers if null
        if (user.getFollower().getSubscriptions() == null) {
            user.getFollower().setSubscriptions(new ArrayList<>());
        }
        if (userfollow.getFollower().getSubscribers() == null) {
            userfollow.getFollower().setSubscribers(new ArrayList<>());
        }

        if(user.getFollower()!=null&&userfollow.getFollower()!=null) {
            if(user.getFollower().getSubscriptions()!=null&&userfollow.getFollower().getSubscribers()!=null) {
                if(user.getFollower().getSubscriptions().contains(userIdToFollow)) {
                    user.getFollower().getSubscriptions().remove(userIdToFollow);
                    userfollow.getFollower().getSubscribers().remove(user.getId());
                    userRepo.save(user);
                    userRepo.save(userfollow);
                    return new FollowResponse("You unfollowed","Follow");
                } else if (!user.getFollower().getSubscriptions().contains(userIdToFollow)) {
                    user.getFollower().getSubscriptions().add(userIdToFollow);
                    userfollow.getFollower().getSubscribers().add(user.getId());
                    userRepo.save(user);
                    userRepo.save(userfollow);
                    return new FollowResponse("You followed","Unfollow");
                }
            } else if (user.getFollower().getSubscriptions() == null&&userfollow.getFollower().getSubscribers()!=null) {
                user.getFollower().setSubscriptions(new ArrayList<>());
                user.getFollower().getSubscriptions().add(userIdToFollow);
                userfollow.getFollower().getSubscribers().add(user.getId());
                userRepo.save(user);
                userRepo.save(userfollow);
            }
        }

        return new FollowResponse("something went wrong");
    }

    @Override
    public List<SubResponse> getSubscribers() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findUserByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));

        List<Long>subscribersId = new ArrayList<>();

        if(user.getFollower()!=null) {
            if(user.getFollower().getSubscribers()!=null) {
                subscribersId = user.getFollower().getSubscribers();
            }
        }
        List<User>users = userRepo.findAllById(subscribersId);
        List<SubResponse> subscribers = new ArrayList<>();
        if(users!=null) {
            for (User u : users) {
                subscribers.add(new SubResponse(
                    u.getId(), u.ownGetName()
                ));
            }
        }

        return subscribers;
    }

    @Override
    public List<SubResponse> getSubscriptions() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findUserByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
        List<Long>subscriptionsId = new ArrayList<>();
        if(user.getFollower()!=null) {
            if (user.getFollower().getSubscriptions() != null) {
                subscriptionsId = user.getFollower().getSubscriptions();
            }
        }
        List<User>users = userRepo.findAllById(subscriptionsId);
        List<SubResponse> subscriptions = new ArrayList<>();
        if(users!=null) {
            for (User u : users) {
                subscriptions.add(new SubResponse(
                        u.getId(), u.ownGetName()
                ));
            }
        }
        return subscriptions;
    }

    @Override
    public FollowerFollowing getFollowerFollowingsOfUser(Long userId) {
        FollowerFollowing response = new FollowerFollowing();

        List<SubResponse>followings = new ArrayList<>();
        List<SubResponse>followers = new ArrayList<>();

        User user = userRepo.findById(userId).orElseThrow(()->new UsernameNotFoundException("User not found"));
        List<Long>followersId = new ArrayList<>();
        List<Long>followingIds = new ArrayList<>();

        if(user.getFollower()!=null) {
            if(user.getFollower().getSubscribers()!=null&&user.getFollower().getSubscriptions()!=null) {
                followersId = user.getFollower().getSubscribers();
                followingIds = user.getFollower().getSubscriptions();
            }
        }

        List<User>subscribers = userRepo.findAllById(followersId);
        List<User>subscriptions = userRepo.findAllById(followingIds);

        if(subscribers!=null&&subscriptions!=null) {
            for (User u : subscribers) {
                followers.add(new SubResponse(
                        u.getId(), u.ownGetName()
                ));
            }
            for (User u : subscriptions) {
                followings.add(new SubResponse(
                        u.getId(), u.ownGetName()
                ));
            }
        }

        response.setFollowers(followers);
        response.setFollowings(followings);
        return response;
    }
}
