package com.momot.trakball.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String name;
    private String surname;

    @OneToOne(mappedBy = "userDetails")
    @JsonIgnoreProperties(value = "userDetails",allowSetters = true)
    private User user;

    public UserDetails(String name, String surname, String phone, User user) {
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.user = user;
    }

    public UserDetails(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
