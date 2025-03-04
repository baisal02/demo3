package com.example.demo.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_gen")
    @SequenceGenerator(sequenceName = "post_seq", name = "post_gen",allocationSize = 1)
    private Long id;
    private String title;
    private String description;
    private LocalDate createdAt;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE})
    private List<Comment> comments;

    @OneToMany(mappedBy = "post",cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Image>images;

    @OneToMany(mappedBy = "post",cascade = {CascadeType.REMOVE})
    private List<Like> likes;

    public Post(String title, String description, LocalDate createdAt, User user, List<Comment> comments, List<Image> images, List<Like> likes) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.user = user;
        this.comments = comments;
        this.images = images;
        this.likes = likes;
    }

    public Post() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Like> getLikes() {
        return likes;
    }
}
