package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

public class PostRequest {
    private String title;
    private String description;
    private List<ImageRequest> imageRequestList;
    private LocalDate localDate;


    public List<ImageRequest> getImageRequestList() {
        return imageRequestList;
    }

    public void setImageRequestList(List<ImageRequest> imageRequestList) {
        this.imageRequestList = imageRequestList;
    }

    public PostRequest(String title, String description, List<ImageRequest> imageRequestList, LocalDate localDate) {
        this.title = title;
        this.description = description;
        this.imageRequestList = imageRequestList;
        this.localDate = localDate;
    }



    public PostRequest() {
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


    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public PostRequest(String title, String description, String imageUrl, LocalDate localDate) {
        this.title = title;
        this.description = description;
        this.localDate = localDate;
    }

}
