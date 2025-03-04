package com.example.demo.dto;

public class UserInfoResponse {
    private Long userId;
    private Long userInfoId;
    private String fullName;
    private String biography;
    private String imageUrl;

    public UserInfoResponse(Long userId, Long userInfoId, String fullName, String biography, String imageUrl) {

        this.userId = userId;
        this.userInfoId = userInfoId;
        this.fullName = fullName;
        this.biography = biography;
        this.imageUrl = imageUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserInfoResponse() {
    }


}
