package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_gen")
    @SequenceGenerator(sequenceName = "image_seq", name = "image_gen",allocationSize = 1)
    private Long id;
    private String imageUrl;

    @OneToOne(mappedBy = "image")
    private User user;

    @ManyToOne
    private Post post;

    public Image(String imageUrl, User user, Post post) {
        this.imageUrl = imageUrl;
        this.user = user;
        this.post = post;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Image() {
    }
}
