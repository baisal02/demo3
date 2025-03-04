package com.example.demo.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_gen")
    @SequenceGenerator(sequenceName = "comment_seq", name = "comment_gen",allocationSize = 1)
    private Long id;
    private String comment;
    private LocalDate createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "comment",cascade = {CascadeType.REMOVE})
    private List<Like> likes;

    public Comment(String comment, LocalDate createdAt, User user, Post post, List<Like> likes) {
        this.comment = comment;
        this.createdAt = createdAt;
        this.user = user;
        this.post = post;
        this.likes = likes;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
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

    public List<Like> getLikes() {
        return likes;
    }



























    public Comment(String comment, LocalDate createdAt) {
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Comment() {
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
}
