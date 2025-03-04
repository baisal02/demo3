package com.example.demo.entities;

import com.example.demo.entities.enums.GENDER;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "userinfos")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userinfo_gen")
    @SequenceGenerator(sequenceName = "userinfo_seq", name = "userinfo_gen",allocationSize = 1)
    private Long id;

    private String fullName;

    private String biography;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserInfo(String fullName, String biography, GENDER gender, String imageUrl, User user) {
        this.fullName = fullName;
        this.biography = biography;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    @NotNull
    @Enumerated(EnumType.STRING) // Store as "MALE" or "FEMALE" in the DB
    @Column(nullable = false)
    private GENDER gender;

    private String imageUrl;

    @OneToOne(mappedBy = "userInfo")
    private User user;
















    public UserInfo(String fullName, String biography, GENDER gender, String imageUrl) {
        this.fullName = fullName;
        this.biography = biography;
        this.gender = gender;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserInfo() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }



}
