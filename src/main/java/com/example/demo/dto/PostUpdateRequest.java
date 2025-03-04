package com.example.demo.dto;

public class PostUpdateRequest {
    private String title;
    private String description;

    public PostUpdateRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PostUpdateRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
