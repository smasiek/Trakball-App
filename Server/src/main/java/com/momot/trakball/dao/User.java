package com.momot.trakball.dao;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "details_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserDetails userDetails;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_squads",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "squad_id"))
    private Set<Squad> squads = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_places",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "place_id"))
    private Set<Place> yourPlaces = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String email, String password, String name, String surname, String phone, String photo) {
        this.email = email;
        this.password = password;
        this.userDetails = new UserDetails(name, surname, phone, photo);
    }

    public User() {
    }

    public Long getUserId() {
        return user_id;
    }

    public void setUser_id(Long id) {
        this.user_id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Squad> getSquads() {
        return squads;
    }

    public void setSquads(Set<Squad> squads) {
        this.squads = squads;
    }

    public Set<Place> getYourPlaces() {
        return yourPlaces;
    }

    public void setYourPlaces(Set<Place> yourPlaces) {
        this.yourPlaces = yourPlaces;
    }

    public String getPhone() {
        return userDetails.getPhone();
    }

    public void setPhone(String phone) {
        this.userDetails.setPhone(phone);
    }

    public String getName() {
        return userDetails.getName();
    }

    public void setName(String name) {
        this.userDetails.setName(name);
    }

    public String getSurname() {
        return userDetails.getSurname();
    }

    public void setSurname(String surname) {
        this.userDetails.setSurname(surname);
    }

    public String getPhoto() {
        return userDetails.getPhoto();
    }
}