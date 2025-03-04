package com.example.demo.dto;

import java.time.LocalDate;

public class CommentResponse {
    private Long id;
    private String comment;
    private LocalDate createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public CommentResponse() {
    }

    public CommentResponse(Long id, String comment, LocalDate createdAt) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}
