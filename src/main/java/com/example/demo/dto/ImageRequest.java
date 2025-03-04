package com.example.demo.dto;

public class ImageRequest {
    private String url;

    public ImageRequest() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageRequest(String url) {
        this.url = url;
    }
}
