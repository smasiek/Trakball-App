package com.momot.trakball.dao;

import com.momot.trakball.dto.PlaceDto;
import com.momot.trakball.dto.request.NewPlaceRequest;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Squad> squads;

    @ManyToMany(mappedBy = "yourPlaces")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<User> followers = new HashSet<>();

    public Place() {
    }

    public Place(PlaceDto placeDto) {
        this.id = placeDto.getPlace_id();
        this.name = placeDto.getName();
        this.city = placeDto.getCity();
        this.postal_code = placeDto.getPostal_code();
        this.street = placeDto.getStreet();
        this.latitude = placeDto.getLatitude();
        this.longitude = placeDto.getLongitude();
        this.photo = placeDto.getPhoto();
    }

    public Place(NewPlaceRequest newPlaceRequest) {
        this.name = newPlaceRequest.getName();
        this.city = newPlaceRequest.getCity();
        this.postal_code = newPlaceRequest.getPostal_code();
        this.street = newPlaceRequest.getStreet();
        this.latitude = newPlaceRequest.getLatitude();
        this.longitude = newPlaceRequest.getLongitude();
    }

    public Place(PlaceRequest placeRequest) {
        this.name = placeRequest.getName();
        this.city = placeRequest.getCity();
        this.postal_code = placeRequest.getPostal_code();
        this.street = placeRequest.getStreet();
        this.latitude = placeRequest.getLatitude();
        this.longitude = placeRequest.getLongitude();
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

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }
}