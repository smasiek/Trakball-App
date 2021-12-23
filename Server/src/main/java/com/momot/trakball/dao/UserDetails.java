package com.momot.trakball.dao;

import javax.persistence.*;

@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_details_id;
    private String phone;
    private String name;
    private String surname;
    private String photo;

    public UserDetails(String name, String surname, String phone, String photo) {
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.photo = photo;
    }

    public UserDetails() {
    }

    public Long getUser_details_id() {
        return user_details_id;
    }

    public void setUser_details_id(Long id) {
        this.user_details_id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}