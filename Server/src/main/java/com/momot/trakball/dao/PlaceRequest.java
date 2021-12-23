package com.momot.trakball.dao;

import com.momot.trakball.dto.PlaceDto;
import com.momot.trakball.dto.request.NewPlaceRequest;

import javax.persistence.*;

@Entity
@Table(name = "place_requests")
public class PlaceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long place_request_id;
    private String name;
    private String city;
    private String postal_code;
    private String street;
    private double latitude;
    private double longitude;
    private String photo;

    @ManyToOne()
    private User requester;

    public PlaceRequest() {
    }

    public PlaceRequest(PlaceDto placeDto) {
        this.place_request_id = placeDto.getPlace_id();
        this.name = placeDto.getName();
        this.city = placeDto.getCity();
        this.postal_code = placeDto.getPostal_code();
        this.street = placeDto.getStreet();
        this.latitude = placeDto.getLatitude();
        this.longitude = placeDto.getLongitude();
        this.photo = placeDto.getPhoto();
    }

    public PlaceRequest(NewPlaceRequest newPlaceRequest, User requester) {
        this.name = newPlaceRequest.getName();
        this.city = newPlaceRequest.getCity();
        this.postal_code = newPlaceRequest.getPostal_code();
        this.street = newPlaceRequest.getStreet();
        this.latitude = newPlaceRequest.getLatitude();
        this.longitude = newPlaceRequest.getLongitude();
        this.requester = requester;
    }

    public Long getPlace_request_id() {
        return place_request_id;
    }

    public void setPlace_request_id(Long id) {
        this.place_request_id = id;
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

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

}