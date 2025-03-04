package com.example.demo.dto;

import java.util.List;

public class FollowerFollowing {
    List<SubResponse> followers;
    List<SubResponse> followings;

    public FollowerFollowing() {
    }

    public List<SubResponse> getFollowers() {
        return followers;
    }

    public void setFollowers(List<SubResponse> followers) {
        this.followers = followers;
    }

    public List<SubResponse> getFollowings() {
        return followings;
    }

    public void setFollowings(List<SubResponse> followings) {
        this.followings = followings;
    }

    public FollowerFollowing(List<SubResponse> followers, List<SubResponse> followings) {
        this.followers = followers;
        this.followings = followings;
    }
}
