package com.example.demo.dto;

import java.time.LocalDate;

public class CommentRequest {
    private String comment;
    private LocalDate localDate;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public CommentRequest(String comment, LocalDate localDate) {
        this.comment = comment;
        this.localDate = localDate;
    }

    public CommentRequest() {
    }
}
