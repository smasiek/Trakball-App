package com.momot.trakball.dao;

import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToOne
    @JoinColumn(name = "details_id")
    private UserDetails userDetails;

    @ManyToOne
    @JoinColumn(name="role_id")
    private UserDetails role;

    public User(Long id, String username, String password, UserDetails userDetails, UserDetails role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userDetails = userDetails;
        this.role = role;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public UserDetails getRole() {
        return role;
    }

    public void setRole(UserDetails role) {
        this.role = role;
    }
}
