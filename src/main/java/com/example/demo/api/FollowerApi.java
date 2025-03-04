package com.example.demo.api;

import com.example.demo.dto.FollowResponse;
import com.example.demo.dto.FollowerFollowing;
import com.example.demo.dto.SubResponse;
import com.example.demo.service.FollowerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follower")
public class FollowerApi {
    private final FollowerService followerService;

    public FollowerApi(FollowerService followerService) {
        this.followerService = followerService;
    }

    @PutMapping("/{userIdToFollow}")
    public FollowResponse follow(@PathVariable Long userIdToFollow){
        return followerService.follow(userIdToFollow);
    }

    @GetMapping("/myFollowers")
    public List<SubResponse> getFollowers(){
        return followerService.getSubscribers();
    }

    @GetMapping("/myFollowings")
    public List<SubResponse> getFollowings(){
        return followerService.getSubscriptions();
    }

    @GetMapping("/viewSomeOnesFollowerFollowingList/{userId}")
    public FollowerFollowing getInfo(@PathVariable Long userId){
        return followerService.getFollowerFollowingsOfUser(userId);
    }
}
