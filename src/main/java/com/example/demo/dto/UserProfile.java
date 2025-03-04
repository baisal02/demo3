package com.example.demo.dto;

import java.util.List;

public class UserProfile {
    private String nickname;
    private String fullName;
    private int subscribersNumber;
    private int subscriptionsNumber;
    private List<PostResponse> postsResponses;
    private ImageResponse imageResponse;
    public UserProfile() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getSubscribersNumber() {
        return subscribersNumber;
    }

    public void setSubscribersNumber(int subscribersNumber) {
        this.subscribersNumber = subscribersNumber;
    }

    public int getSubscriptionsNumber() {
        return subscriptionsNumber;
    }

    public void setSubscriptionsNumber(int subscriptionsNumber) {
        this.subscriptionsNumber = subscriptionsNumber;
    }

    public List<PostResponse> getPostsResponses() {
        return postsResponses;
    }

    public void setPostsResponses(List<PostResponse> postsResponses) {
        this.postsResponses = postsResponses;
    }

    public ImageResponse getImageResponse() {
        return imageResponse;
    }

    public void setImageResponse(ImageResponse imageResponse) {
        this.imageResponse = imageResponse;
    }

    public UserProfile(String nickname, String fullName, int subscribersNumber, int subscriptionsNumber, List<PostResponse> postsResponses, ImageResponse imageResponse) {
        this.nickname = nickname;
        this.fullName = fullName;
        this.subscribersNumber = subscribersNumber;
        this.subscriptionsNumber = subscriptionsNumber;
        this.postsResponses = postsResponses;
        this.imageResponse = imageResponse;
    }


}
