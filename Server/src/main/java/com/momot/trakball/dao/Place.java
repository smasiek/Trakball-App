package com.momot.trakball.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "places")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String postal_code;
    private String street;
    private double latitude;
    private double longitude;
    private String photo;

    @OneToMany(mappedBy = "place")
    @JsonIgnoreProperties(value = "place",allowSetters = true)
    private Set<Squad> squads;

    public Place(Long id, String name, String city, String postal_code, String street, double latitude, double longitude, String photo) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.postal_code = postal_code;
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photo = photo;
    }

    public Place() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<Squad> getSquads() {
        return squads;
    }

    public void setSquads(Set<Squad> squads) {
        this.squads = squads;
    }
}
