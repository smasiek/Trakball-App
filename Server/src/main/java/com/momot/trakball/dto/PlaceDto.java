package com.momot.trakball.dto;

public class PlaceDto {

    private Long place_id;
    private String name;
    private String city;
    private String postal_code;
    private String street;
    private double latitude;
    private double longitude;
    private String photo;

    public PlaceDto() {
    }

    public PlaceDto(Long place_id, String name, String city, String postal_code, String street, double latitude, double longitude, String photo) {
        this.place_id = place_id;
        this.name = name;
        this.city = city;
        this.postal_code = postal_code;
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photo = photo;
    }

    public Long getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Long place_id) {
        this.place_id = place_id;
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
}
