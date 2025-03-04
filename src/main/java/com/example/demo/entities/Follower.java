package com.example.demo.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "followers")
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "follower_gen")
    @SequenceGenerator(sequenceName = "follower_seq", name = "follower_gen",allocationSize = 1)
    private Long id;

    @OneToOne(mappedBy = "follower")
    private User user;

    @ElementCollection
    private List<Long>subscribers;

    @ElementCollection
    private List<Long>subscriptions;

    public Follower() {
    }

    public Follower(User user, List<Long> subscribers, List<Long> subscriptions) {
        this.user = user;
        this.subscribers = subscribers;
        this.subscriptions = subscriptions;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSubscribers(List<Long> subscribers) {
        this.subscribers = subscribers;
    }

    public void setSubscriptions(List<Long> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<Long> getSubscribers() {
        return subscribers;
    }

    public List<Long> getSubscriptions() {
        return subscriptions;
    }
}
