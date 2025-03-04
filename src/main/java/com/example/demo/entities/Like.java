package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_gen")
    @SequenceGenerator(sequenceName = "like_seq", name = "like_gen",allocationSize = 1)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    @ManyToOne
    private Comment comment;
    public void setPost(Post post) {
        this.post = post;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Like() {
    }

    public Like(Post post, User user, Comment comment) {
        this.post = post;
        this.user = user;
        this.comment = comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public User getUser() {
        return user;
    }

    public Comment getComment() {
        return comment;
    }





}
