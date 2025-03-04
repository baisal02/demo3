package com.example.demo.dto;

public class AuthResponse {
    private Long id;
    private String email;
    private String token;




    public Long getId() {
        return id;
    }

    public AuthResponse() {
    }

    public AuthResponse(Long id, String email, String token) {

        this.id = id;
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
