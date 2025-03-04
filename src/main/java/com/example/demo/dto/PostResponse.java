package com.example.demo.dto;

import com.example.demo.entities.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PostResponse {
    private Long id;
    private String title;
    private String description;
    private List<ImageResponse>images;

    public PostResponse() {
    }

    public List<ImageResponse> getImages() {
        return images;
    }

    public void setImages(List<ImageResponse> images) {
        this.images = images;
    }

    public PostResponse(Long id, String title, String description, List<ImageResponse> images, LocalDate creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.images = images;
        this.creationDate = creationDate;
    }

    private LocalDate creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public PostResponse(Long id, String title, String description, LocalDate creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
    }
}
