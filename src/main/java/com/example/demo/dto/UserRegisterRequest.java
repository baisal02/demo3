package com.example.demo.dto;

import com.example.demo.entities.enums.GENDER;

public class UserRegisterRequest {
    private String nickname;
    private String firstame;
    private String surname;
    private String phonenumber;
    private GENDER gender;
    private String email;
    private String password;

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(String nickname, String firstame, String surname, String phonenumber, GENDER gender, String email, String password) {
        this.nickname = nickname;
        this.firstame = firstame;
        this.surname = surname;
        this.phonenumber = phonenumber;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstame() {
        return firstame;
    }

    public void setFirstame(String firstame) {
        this.firstame = firstame;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
