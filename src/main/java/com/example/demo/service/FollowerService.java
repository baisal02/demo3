package com.example.demo.service;

import com.example.demo.dto.FollowResponse;
import com.example.demo.dto.FollowerFollowing;
import com.example.demo.dto.SimpleResponse;
import com.example.demo.dto.SubResponse;

import java.util.List;

public interface FollowerService {
    FollowResponse follow(Long userIdToFollow);    //button follow
    List<SubResponse> getSubscribers();   //own datas
    List<SubResponse> getSubscriptions();

    FollowerFollowing getFollowerFollowingsOfUser(Long userId);
}
