package com.momot.trakball.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewPlaceRequest {

    @NotBlank
    @Size(min = 1, max = 200)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String street;

    @NotBlank
    @Size(max = 200)
    private String city;

    @NotBlank
    private double latitude;

    @NotBlank
    private double longitude;

    @NotBlank
    @Size(max = 200)
    private String postal_code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }
}