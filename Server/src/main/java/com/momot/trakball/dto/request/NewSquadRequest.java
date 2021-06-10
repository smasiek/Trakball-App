package com.momot.trakball.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewSquadRequest {

    @NotBlank
    @Size(min=2,max = 200)
    private String city;

    @NotBlank
    @Size(min=2,max = 200)
    private String street;

    @NotBlank
    @Size(min=2,max = 200)
    private String place;

    @NotBlank
    @Size(min=2,max = 200)
    private String sport;

    private int maxMembers;

    @NotBlank
    @Size(min=0,max = 50)
    private String fee;

    @NotBlank
    @Size(min=2,max = 50)
    private String date;


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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}
