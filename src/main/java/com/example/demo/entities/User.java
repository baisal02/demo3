package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(sequenceName = "user_seq", name = "user_gen",allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;


    @Column(nullable = false)
    @Pattern(regexp = "\\+996\\d{6,}", message = "Phone number must start with +996 and contain at least 6 digits")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Image image;

    @OneToOne(cascade = CascadeType.ALL)
    private Follower follower;

    @OneToOne(cascade = CascadeType.ALL)
    private UserInfo userInfo;



    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    private List<Comment> comments;



    @OneToMany(mappedBy = "user",cascade = {CascadeType.REMOVE})
    private List<Post> posts;




    @OneToMany(mappedBy = "user",cascade = {CascadeType.REMOVE})
    private List<Like>likes;



















    public String ownGetName() {
        return username;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Follower getFollower() {
        return follower;
    }

    public void setFollower(Follower follower) {
        this.follower = follower;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public User(String username, String email, String password, String phoneNumber, Image image, Follower follower, UserInfo userInfo, List<Comment> comments, List<Post> posts, List<Like> likes) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.follower = follower;
        this.userInfo = userInfo;
        this.comments = comments;
        this.posts = posts;
        this.likes = likes;
    }

    public Long getId() {
        return id;
    }

    public String ownGetPassword() {
        return password;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public User(String username, String password, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
