package com.example.demo.dto;

import com.example.demo.entities.enums.GENDER;

public class UpdateUserRequest {
   private String nickname;
   private String biography;
   private String phoneNumber;
   private GENDER gender;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String nickname, String biography, String phoneNumber, GENDER gender) {
        this.nickname = nickname;
        this.biography = biography;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
}
