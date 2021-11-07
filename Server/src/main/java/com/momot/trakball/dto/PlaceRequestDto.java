package com.momot.trakball.dto;

public class PlaceRequestDto {

    private Long place_request_id;
    private String name;
    private String city;
    private String postal_code;
    private String street;
    private double latitude;
    private double longitude;
    private String photo;
    private Long requester_id;
    private String requester;

    public PlaceRequestDto(Long place_request_id, String name, String city, String postal_code, String street,
                           double latitude, double longitude, String photo, Long requester_id, String requester) {
        this.place_request_id = place_request_id;
        this.name = name;
        this.city = city;
        this.postal_code = postal_code;
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photo = photo;
        this.requester_id = requester_id;
        this.requester = requester;
    }

    public Long getPlace_request_id() {
        return place_request_id;
    }

    public void setPlace_request_id(Long place_request_id) {
        this.place_request_id = place_request_id;
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

    public Long getRequester_id() {
        return requester_id;
    }

    public void setRequester_id(Long requester_id) {
        this.requester_id = requester_id;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }
}
