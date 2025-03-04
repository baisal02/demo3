package com.example.demo.dto;

public class FollowResponse {
    private String message;
    private String followInfo;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FollowResponse(String message, String followInfo) {
        this.message = message;
        this.followInfo = followInfo;
    }


    public String getFollowInfo() {
        return followInfo;
    }

    public void setFollowInfo(String followInfo) {
        this.followInfo = followInfo;
    }

    public FollowResponse() {
    }

    public FollowResponse(String followInfo) {
        this.followInfo = followInfo;
    }


}
