package com.example.demo.dto;

import org.springframework.http.HttpStatus;

public class SimpleResponse {
    private String message;
    private HttpStatus status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public SimpleResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
